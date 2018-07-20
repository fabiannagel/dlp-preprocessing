package com.dlp.BoundingBox;

import com.dlp.DataSetConstants;
import com.dlp.DataType;

import javax.xml.crypto.Data;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
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
    private String fileNamePrefix;

    private PrintWriter writer;
    private File outputDirectory;


    /**
     *
     * @param boundingBoxesPerFramesSet
     * @param imagesWithoutBoundingBoxes
     * @param outputFilePath
     * @param dataType
     * @param fileNamePrefix prefix that will be used when writing the CSV file name
     */
    public BoundingBoxesPerFrameWriter(List<BoundingBoxesPerFrame> boundingBoxesPerFramesSet, List<File> imagesWithoutBoundingBoxes, String outputFilePath, DataType dataType, String fileNamePrefix) throws IOException {
        this.boundingBoxesPerFramesSet = boundingBoxesPerFramesSet;
        this.imagesWithoutBoundingBoxes = imagesWithoutBoundingBoxes;
        this.outputFilePath = new File(outputFilePath);
        this.dataType = dataType;
        this.fileNamePrefix = fileNamePrefix;

        createDirectoryIfNotExisting(this.outputFilePath);

        // for example:     /User/Downloads/preprocessed_dataset    +   val_set    +    images
        Path outputPath = Paths.get(this.outputFilePath.getAbsolutePath(), this.dataType.getSubFolderName(), DataSetConstants.OUTPUT_LABELS_SUBFOLDER);
        outputDirectory = new File(outputPath.toString());
        Files.createDirectories(outputDirectory.toPath());
    }

    public void writeBoundingBoxFiles() throws IOException {
        for (BoundingBoxesPerFrame boundingBoxesPerFrame : this.boundingBoxesPerFramesSet) {

            File imageFile = boundingBoxesPerFrame.getImageFile();
            String labelFileName = getLabelFileNameFromImageFile(imageFile);
            File outputLabelFile = new File(this.outputDirectory, labelFileName);

            writer = new PrintWriter(outputLabelFile);

            for (BoundingBox boundingBox : boundingBoxesPerFrame.getBoundingBoxes()) {
                String newLine = boundingBox.getDarknetRepresentation();
                writer.println(newLine);
            }
            writer.close();
        }
    }

    // writes empty files for all images which don't have any bounding boxes assigned
    public void writeEmptyBoundingBoxFiles() throws IOException {

        for (File image : this.imagesWithoutBoundingBoxes) {
            String labelFileName = getLabelFileNameFromImageFile(image);
            File outputLabelFile = new File(this.outputDirectory, labelFileName);

            writer = new PrintWriter(new FileOutputStream(outputLabelFile));
            writer.close();
        }
    }

    // returns a string like "Yangon_II_19_01.jpg"
    private String getLabelFileNameFromImageFile(File imageFile) {
        String imageFileName = this.fileNamePrefix + "_" + imageFile.getName();
        return imageFileName.replaceAll(DataSetConstants.IMAGE_FILE_EXTENSION, DataSetConstants.TEXT_FILE_EXTENSION);
    }

    private void createDirectoryIfNotExisting(File f) {
        boolean directoryCreated = f.mkdir();
        if (!directoryCreated && !f.exists()) {
            throw new RuntimeException("Couldn't create output directory at " + f.getAbsolutePath());
        }
    }

    public void debugOutput() {
        for (BoundingBoxesPerFrame boundingBoxesPerFrame : this.boundingBoxesPerFramesSet) {
            String frameId = String.valueOf(boundingBoxesPerFrame.getFrameId());

            File imageFolder = boundingBoxesPerFrame.getImageFile();

            System.out.println(frameId + ": " + imageFolder.getAbsolutePath().toString());

            for (BoundingBox boundingBox : boundingBoxesPerFrame.getBoundingBoxes()) {
                System.out.println(boundingBox.getDarknetRepresentation());

            }

            System.out.println("====================================");
        }
    }
}
