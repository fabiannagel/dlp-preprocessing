package com.dlp.BoundingBox;

import com.dlp.City;
import com.dlp.DataSetConstants;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

// takes a list of BoundingBoxes and groups them by frame id
// for each group, a new BoundingBoxesPerFrame object is created
// a list of those is returned

public class BoundingBoxesPerFrameFactory {

    private List<BoundingBox> allBoundingBoxes;
    private City city;  // the sub-dataset we're operating on

    private final List<BoundingBoxesPerFrame> boundingBoxesPerFrameSet = new ArrayList<>();

    public BoundingBoxesPerFrameFactory(List<BoundingBox> allBoundingBoxes, City city) {
        this.allBoundingBoxes = allBoundingBoxes;
        this.city = city;

        groupByFrameId();
    }

    // creates clusters of boxes by frame id
    // if a frame id has no defined bounding box, an empty object (for later serialization) is created
    private  void groupByFrameId() {
        HashMap<Integer, List<BoundingBox>> boundingBoxesPerFrameHashMap = new HashMap<Integer, List<BoundingBox>>();

        for (BoundingBox boundingBox: this.allBoundingBoxes) {
            int key = boundingBox.getFid();

            // if the frame id doesn't exist yet as a key, create a new empty list.
            // add the bounding box to it.
            if (!boundingBoxesPerFrameHashMap.containsKey(key)) {
                ArrayList<BoundingBox> list = new ArrayList<>();
                list.add(boundingBox);

                boundingBoxesPerFrameHashMap.put(key, list);
                continue;
            }

            // otherwise, get the existing list from the hash map.
            // add the bounding box to it.
            List<BoundingBox> existingList = boundingBoxesPerFrameHashMap.get(key);
            existingList.add(boundingBox);
        }

        // iterate over the frame IDs and create BoundingBoxesPerFrame objects
        for (Integer frameId : boundingBoxesPerFrameHashMap.keySet()) {
            List<BoundingBox> correspondingBoundingBoxes = boundingBoxesPerFrameHashMap.get(frameId);

            File imageFile = getImageFileFromFrameId(frameId);

            BoundingBoxesPerFrame box = new BoundingBoxesPerFrame(frameId, correspondingBoundingBoxes, imageFile);
            boundingBoxesPerFrameSet.add(box);
        }
    }

    private File getImageFileFromFrameId(int frameId) {
        File imagesFolder = this.city.getImagesFolder();

        // the frame id might be 7, but the corresponding file name is "07.jpg"

        String imageFileName = getImageFileNameFromFrameId(frameId);
        List<File> imageFile = Arrays.stream(imagesFolder.listFiles()).filter(f -> f.getName().equals(imageFileName)).collect(Collectors.toList());

        // there should only be one match
        assert imageFile.size() == 1;

        return imageFile.get(0);
    }

    private String getImageFileNameFromFrameId(int frameId) {
        String prefix = StringUtils.EMPTY;
        if (frameId < 10) {
            prefix = "0";
        }

        return prefix + frameId + DataSetConstants.IMAGE_FILE_EXTENSION;
    }

    // some image files might not have a motor bike in them.
    // hence, the frame id appeared nowhere in the label CSV files
    public List<File> getImagesWithoutBoundingBoxes() {

        // all files in the current sub dataset
        List<File> allImageFiles = new ArrayList<>(Arrays.asList(this.city.getImagesFolder().listFiles()));

        // all files which have bounding boxes assigned to them
        List<File> imageFilesWithBoundingBoxes = this.boundingBoxesPerFrameSet.stream().map(b -> b.getImageFile()).collect(Collectors.toList());

        // All that's left are images without any assigned bounding boxes
        allImageFiles.removeAll(imageFilesWithBoundingBoxes);

        return allImageFiles;
    }

    public List<BoundingBoxesPerFrame> getBoundingBoxesPerFrameSet() {
        return boundingBoxesPerFrameSet;
    }
}
