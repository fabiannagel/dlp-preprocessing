package com.dlp.BoundingBox;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class BoundingBoxesPerFrame {

    private int frameId;
    private List<BoundingBox> boundingBoxes;
    private List<BoundingBox> corruptBoundingBoxes;
    private File imageFile;     // the concrete image file for which the bounding boxes are described

    public BoundingBoxesPerFrame(int frameId, List<BoundingBox> boundingBoxes, File imageFile) {
        this.frameId = frameId;
        this.imageFile = imageFile;


        // save corrupted and non-corrupted bounding boxes
        this.boundingBoxes = boundingBoxes.stream().filter(b -> !b.isCorrupted()).collect(Collectors.toList());
        this.corruptBoundingBoxes = boundingBoxes.stream().filter(b -> b.isCorrupted()).collect(Collectors.toList());


        // to check if a whole motorbike instance is removed due to corruption
        if (this.boundingBoxes.size() == 0 && this.corruptBoundingBoxes.size() > 0) {
            System.out.println("All bounding boxes are removed due to corruption");
        }
    }

    public int getFrameId() {
        return frameId;
    }

    public List<BoundingBox> getCorruptBoundingBoxes() {
        return corruptBoundingBoxes;
    }

    public List<BoundingBox> getBoundingBoxes() {
        return boundingBoxes;
    }

    public File getImageFile() {
        return imageFile;
    }
}
