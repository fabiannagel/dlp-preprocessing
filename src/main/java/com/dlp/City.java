package com.dlp;

import com.dlp.BoundingBox.BoundingBox;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class City {
    private File csvFile;
    private File imagesFolder;
    private List<BoundingBox> boundingBoxes;        // The lines of the parsed CSV file

    private static final char CSV_SEPARATOR = ',';


    public City(File csvFile, File imagesFolder) {
        this.csvFile = csvFile;
        this.imagesFolder = imagesFolder;
        parseCsvFile();
    }

    private void parseCsvFile() {
        try {
            List<BoundingBox> parsedBoundingBoxes = new CsvToBeanBuilder(new FileReader(this.csvFile))
                    .withType(BoundingBox.class).build().parse();

            // save ALL bounding boxes here but return only the non-corrupted ones in the corresponding
            // BoundingBoxesPerFrame object
            this.boundingBoxes = parsedBoundingBoxes;

            // remove all corrupted bounding boxes
            // this.boundingBoxes = parsedBoundingBoxes.stream().filter(b -> !b.isCorrupted()).collect(Collectors.toList());
        } catch (IOException e) { e.printStackTrace(); }
    }

    public File getCsvFile() {
        return csvFile;
    }

    public File getImagesFolder() {
        return imagesFolder;
    }

    public List<BoundingBox> getBoundingBoxes() {
        return boundingBoxes;
    }

    @Override
    public String toString() {
        return "csv file:\t\t"
                + this.csvFile.getAbsolutePath()
                + "\nimages folder:\t"
                + this.imagesFolder.getAbsolutePath()
                + "\n" + this.imagesFolder.listFiles().length + " images"
                + "\n=============================";
    }
}
