package com.dlp;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CityImagesWriter {

    private City city;
    private File outputFilePath;
    private DataType dataType;
    private String fileNamePrefix;

    public CityImagesWriter(City city, String outputFilePath, DataType dataType, String fileNamePrefix) {
        this.city = city;
        this.outputFilePath = new File(outputFilePath);
        this.dataType = dataType;
        this.fileNamePrefix = fileNamePrefix;

        createDirectoryIfNotExisting(this.outputFilePath);
    }

    public void writeImages() throws IOException {
        File[] allImages = city.getImagesFolder().listFiles();

        Path outputPath = Paths.get(outputFilePath.getAbsolutePath(), this.dataType.getSubFolderName(), DataSetConstants.OUTPUT_IMAGE_SUBFOLDER);
        File outputDirectory = new File(outputPath.toString());

        Files.createDirectories(outputDirectory.toPath());

        for (File sourceImage : allImages) {

            String destinationFileName = getDestinationFileName(sourceImage);
            File destinationFile = new File(outputDirectory, destinationFileName);

            FileUtils.copyFile(sourceImage, destinationFile);
        }


    }

    private String getDestinationFileName(File sourceImage) {
        return this.fileNamePrefix + "_" + sourceImage.getName();
    }






    private void createDirectoryIfNotExisting(File f) {
        boolean directoryCreated = f.mkdir();
        if (!directoryCreated && !f.exists()) {
            throw new RuntimeException("Couldn't create output directory at " + f.getAbsolutePath());
        }
    }
}
