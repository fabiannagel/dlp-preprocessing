package com.dlp.BoundingBox;

import com.dlp.DataSetConstants;
import com.dlp.DataType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

// writes all bounding boxes of the given BoundingBoxesPerFrame object into a single file.
// doesn't account for frames which don't have any existing bounding boxes
public class BoundingBoxesPerFrameWriter {

    private List<BoundingBoxesPerFrame> boundingBoxesPerFramesSet;
    private List<File> imagesWithoutBoundingBoxes;
    private File outputFilePath;
    private DataType dataType;


    public BoundingBoxesPerFrameWriter(List<BoundingBoxesPerFrame> boundingBoxesPerFramesSet, List<File> imagesWithoutBoundingBoxes, String outputFilePath, DataType dataType) {
        this.boundingBoxesPerFramesSet = boundingBoxesPerFramesSet;
        this.imagesWithoutBoundingBoxes = imagesWithoutBoundingBoxes;
        this.outputFilePath = new File(outputFilePath);
        this.dataType = dataType;

        boolean directoryCreated = this.outputFilePath.mkdir();
        if (!directoryCreated && !this.outputFilePath.exists()) {
            throw new RuntimeException("Couldn't create output directory at " + outputFilePath);
        }
    }

    public void writeBoundingBoxFiles() throws IOException {

        for (BoundingBoxesPerFrame boundingBoxesPerFrame : this.boundingBoxesPerFramesSet) {
            String frameId = String.valueOf(boundingBoxesPerFrame.getFrameId());

            File imageFolder = boundingBoxesPerFrame.getImageFile();

            System.out.println(frameId + ": " + imageFolder.getAbsolutePath().toString());

            for (BoundingBox boundingBox : boundingBoxesPerFrame.getBoundingBoxes()) {
                System.out.println(boundingBox.getDarknetRepresentation());

            }

            System.out.println("====================================");
        }


        String baseOutputPath = Paths.get(outputFilePath.getAbsolutePath(), this.dataType.getSubFolderName()).toString();
        baseOutputPath = Paths.get(baseOutputPath, this.dataType.getSubFolderName()).toString();

        String fileName = getTxtFileNameFromImageFile(this.boundingBoxesPerFramesSet.get(0).getImageFile());

        System.out.println(baseOutputPath + fileName);

        // TODO: there needs to be a labels subdirectory
    }

    // writes empty files for all images which don't have any bounding boxes assigned
    public void writeEmptyBoundingBoxFiles() throws IOException {

        // TODO: create empty txt files at the locations of all imagesWithoutBoundingBoxes files


    }

    private String getTxtFileNameFromImageFile(File imageFile) {
        String imageFileName = imageFile.getName();
        return imageFileName.replaceAll(".jpg", ".txt");
    }
}
