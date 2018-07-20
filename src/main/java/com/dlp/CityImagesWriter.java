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
    private final File outputDirectory;

    public CityImagesWriter(City city, String outputFilePath, DataType dataType, String fileNamePrefix) throws IOException {
        this.city = city;
        this.outputFilePath = new File(outputFilePath);
        this.dataType = dataType;
        this.fileNamePrefix = fileNamePrefix;

        createDirectoryIfNotExisting(this.outputFilePath);

        Path outputPath = Paths.get(this.outputFilePath.getAbsolutePath(), this.dataType.getSubFolderName(), DataSetConstants.OUTPUT_IMAGE_SUBFOLDER);
        outputDirectory = new File(outputPath.toString());

        Files.createDirectories(outputDirectory.toPath());
    }

    public void writeImages() throws IOException {
        File[] allImages = city.getImagesFolder().listFiles();

        for (File sourceImage : allImages) {
            String destinationFileName = getDestinationFileName(sourceImage);
            File destinationFile = new File(outputDirectory, destinationFileName);

            // TODO: create an option to move the files instead of copying

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
