package com.dlp;

import com.dlp.BoundingBox.BoundingBox;
import com.dlp.BoundingBox.BoundingBoxesPerFrame;
import com.dlp.BoundingBox.BoundingBoxesPerFrameFactory;
import com.dlp.BoundingBox.BoundingBoxesPerFrameWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    // TODO: make this an argument
    private static final String DATASET_BASE_DIR = "/Users/fabian/Downloads/dataset";

    // TODO: make this an argument
    private static final String OUTPUT_PATH = "/Users/fabian/Downloads/preprocessed_dataset";

    public static void main(String[] args) {

        // TODO: make this an argument
        DataType dataType = DataType.VALIDATION;


        CityFactory cityFactory = new CityFactory(DATASET_BASE_DIR, dataType);
        List<City> cities = cityFactory.createCityObjects();


        // TODO: loop over all cities
        City city = cities.get(0);

        List<BoundingBox> boundingBoxes = city.getBoundingBoxes();

        BoundingBoxesPerFrameFactory boundingBoxesPerFrameFactory = new BoundingBoxesPerFrameFactory(boundingBoxes, city);

        List<BoundingBoxesPerFrame> boundingBoxesPerFrameSet = boundingBoxesPerFrameFactory.getBoundingBoxesPerFrameSet();
        List<File> imagesWithoutBoundingBoxes = boundingBoxesPerFrameFactory.getImagesWithoutBoundingBoxes();


        String fileNamePrefix = city.getImagesFolder().getName();

        BoundingBoxesPerFrameWriter boundingBoxWriter = new BoundingBoxesPerFrameWriter(boundingBoxesPerFrameSet, imagesWithoutBoundingBoxes, OUTPUT_PATH, dataType, fileNamePrefix);

        CityImagesWriter cityImagesWriter = new CityImagesWriter(city, OUTPUT_PATH, dataType, fileNamePrefix);

        try {
            boundingBoxWriter.writeBoundingBoxFiles();
            cityImagesWriter.writeImages();

        } catch (IOException e) { e.printStackTrace(); }
    }
}
