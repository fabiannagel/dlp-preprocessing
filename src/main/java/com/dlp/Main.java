package com.dlp;

import com.dlp.BoundingBox.BoundingBox;
import com.dlp.BoundingBox.BoundingBoxesPerFrame;
import com.dlp.BoundingBox.BoundingBoxesPerFrameFactory;

import java.io.File;
import java.util.List;

public class Main {

    private static final String DATASET_BASE_DIR = "/Users/fabian/Downloads/dataset";

    public static void main(String[] args) {

        CityFactory cityFactory = new CityFactory(DATASET_BASE_DIR, DataType.VALIDATION);
        List<City> cities = cityFactory.createCityObjects();

//        for (City city : cities) {
//            List<BoundingBox> boundingBoxes = city.getBoundingBoxes();
//
//            BoundingBoxesPerFrameFactory boundingBoxesPerFrameFactory = new BoundingBoxesPerFrameFactory(boundingBoxes, city);
//            List<BoundingBoxesPerFrame> boundingBoxesPerFrameSet = boundingBoxesPerFrameFactory.getBoundingBoxesPerFrameSet();
//
//            List<File> imagesWithoutBoundingBoxes = boundingBoxesPerFrameFactory.getImagesWithoutBoundingBoxes();
//
//            assert boundingBoxesPerFrameSet.size() + imagesWithoutBoundingBoxes.size() == DataSetConstants.NUMBER_OF_IMAGES_PER_SUB_DATASET;
//        }

        City city = cities.get(0);

        List<BoundingBox> boundingBoxes = city.getBoundingBoxes();

        BoundingBoxesPerFrameFactory boundingBoxesPerFrameFactory = new BoundingBoxesPerFrameFactory(boundingBoxes, city);

        List<BoundingBoxesPerFrame> boundingBoxesPerFrameSet = boundingBoxesPerFrameFactory.getBoundingBoxesPerFrameSet();
        List<File> imagesWithoutBoundingBoxes = boundingBoxesPerFrameFactory.getImagesWithoutBoundingBoxes();


        for (BoundingBoxesPerFrame boundingBoxesPerFrame : boundingBoxesPerFrameSet) {
            int frameId = boundingBoxesPerFrame.getFrameId();
            String imageFolderPath = boundingBoxesPerFrame.getImageFile().toString();

            System.out.println(String.valueOf(frameId) + ": " + imageFolderPath);

            for (BoundingBox boundingBox : boundingBoxesPerFrame.getBoundingBoxes()) {
                System.out.println(boundingBox.getDarknetRepresentation());

            }

            System.out.println("====================================");
        }
    }
}
