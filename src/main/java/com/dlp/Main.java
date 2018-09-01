package com.dlp;

import com.dlp.BoundingBox.BoundingBox;
import com.dlp.BoundingBox.BoundingBoxesPerFrame;
import com.dlp.BoundingBox.BoundingBoxesPerFrameFactory;
import com.dlp.BoundingBox.BoundingBoxesPerFrameWriter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.cli.*;

public class Main {
    private static String DATASET_BASE_DIR;
    private static String OUTPUT_PATH;
    private static DataType DATA_TYPE;

    // prevents disk writes for debugging
    public static boolean DRY_RUN = false;

    public static void main(String[] args) {

        Options options = new Options();

        Option input = new Option("i", "input_dataset", true, "path to the input data set");
        input.setRequired(true);
        options.addOption(input);

        Option output = new Option("o", "output_directory", true, "output directory for the postprocessed dataset");
        output.setRequired(true);
        options.addOption(output);

        Option dataMode = new Option("d", "data_mode", true, "the subset of the dataset to process (training or validation)");
        output.setRequired(true);
        options.addOption(dataMode);

        CommandLineParser parser = new DefaultParser();
        HelpFormatter formatter = new HelpFormatter();
        CommandLine cmd;

        if (DRY_RUN) printDryRunWarning();

        try {
            cmd = parser.parse(options, args);

            DATASET_BASE_DIR = cmd.getOptionValue("input_dataset");
            OUTPUT_PATH = cmd.getOptionValue("output_directory");
            String dataModeString = cmd.getOptionValue("data_mode");

            if (dataModeString.toLowerCase().equals("training")) {
                DATA_TYPE = DataType.TRAINING;
            } else if (dataModeString.toLowerCase().equals("validation")) {
                DATA_TYPE = DataType.VALIDATION;
            } else if (dataModeString.toLowerCase().equals("test")) {
                DATA_TYPE = DataType.TEST;
            } else {
                throw new ParseException("The requested data_mode is unknown");
            }

            printInfo();

            runPreprocessing();

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            formatter.printHelp("utility-name", options);
            System.exit(1);
        }
    }

    private static void printInfo() {
        System.out.println("\nStarting dataset preprocessing");

        System.out.println("Running in " + DATA_TYPE.toString().toLowerCase() + " mode");
        System.out.println("Dataset base directory: " + DATASET_BASE_DIR);
        System.out.println("Output directory: " + OUTPUT_PATH + "\n");
    }

    private static void runPreprocessing() {
        CityFactory cityFactory = new CityFactory(DATASET_BASE_DIR, DATA_TYPE);
        List<City> cities = cityFactory.createCityObjects();

        for (City city : cities) {
            printProgress(city, cities.indexOf(city) + 1, cities.size());

            List<BoundingBox> boundingBoxes = city.getBoundingBoxes();
            BoundingBoxesPerFrameFactory boundingBoxesPerFrameFactory = new BoundingBoxesPerFrameFactory(boundingBoxes, city);

            List<BoundingBoxesPerFrame> boundingBoxesPerFrameSet = boundingBoxesPerFrameFactory.getBoundingBoxesPerFrameSet();
            List<File> imagesWithoutBoundingBoxes = boundingBoxesPerFrameFactory.getImagesWithoutBoundingBoxes();

            String fileNamePrefix = DATA_TYPE.getFileSuffix() + "_" + city.getImagesFolder().getName();

            try {
                BoundingBoxesPerFrameWriter boundingBoxWriter = new BoundingBoxesPerFrameWriter(boundingBoxesPerFrameSet, imagesWithoutBoundingBoxes, OUTPUT_PATH, DATA_TYPE, fileNamePrefix);
                CityImagesWriter cityImagesWriter = new CityImagesWriter(city, OUTPUT_PATH, DATA_TYPE, fileNamePrefix);

                boundingBoxWriter.writeBoundingBoxFiles();
                boundingBoxWriter.writeEmptyBoundingBoxFiles();
                cityImagesWriter.writeImages();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println("\nPreprocessing finished!");
    }

    private static void printProgress(City city, int index, int total) {
        String prefix = "(" + index + "/" + total + ")";
        String message = "Processing " + city.getImagesFolder().getName();

        String format = "%-20s%s%n";
        System.out.printf(format, prefix, message);
    }

    private static void printDryRunWarning() {
        String ANSI_RED = "\u001B[31m";
        String ANSI_RESET = "\u001B[0m";

        System.out.println();
        System.out.println(ANSI_RED + "WARNING" + ANSI_RESET);
        System.out.println(ANSI_RED + "Dry run is enabled. Nothing will be written to disk!" + ANSI_RESET);
        System.out.println();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) { }
    }
}
