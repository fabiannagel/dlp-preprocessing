package com.dlp;

import com.dlp.BoundingBox.BoundingBox;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class GroundTruth {

    private File csvFile;
    private CSVReader csvReader;

    private List<BoundingBox> lines;

    private static final char CSV_SEPARATOR = ',';

    public GroundTruth(File csvFile) throws IOException {
        this.csvFile = csvFile;
        this.csvReader = new CSVReader(new FileReader(csvFile));
        parseCsvFile();
    }

    public List<BoundingBox> getLines() {
        return lines;
    }

    private void parseCsvFile() {
        try {
            this.lines = new CsvToBeanBuilder(new FileReader(this.csvFile))
                    .withType(BoundingBox.class).build().parse();

        } catch (IOException e) {

        }
    }


}
