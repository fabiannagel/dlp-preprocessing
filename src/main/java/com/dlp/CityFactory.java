package com.dlp;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.cert.CollectionCertStoreParameters;
import java.util.*;
import java.util.stream.Collectors;

public class CityFactory {
    private DataType dataType;
    private Path workingDirectory;

    private static final String CSV_FILE_EXTENSION = ".csv";

    public CityFactory(String datasetBaseDir, DataType dataType) {
        this.dataType = dataType;
        this.workingDirectory = Paths.get(datasetBaseDir, dataType.getSubFolderName());
    }

    public List<City> createCityObjects() {
        // list first-level files and folders
        // group them by name

        File dataSet = new File(workingDirectory.toString());

        File[] allFiles = dataSet.listFiles();
        List<File> folders = Arrays.stream(allFiles).filter(f -> f.isDirectory()).collect(Collectors.toList());
        List<File> files = Arrays.stream(allFiles).filter(f -> f.isFile()).collect(Collectors.toList());

        // a healthy dataset has one images folder for every CSV file
        assert folders.size() == files.size();

        List<City> cities = new ArrayList<>();

        for (File folder : folders) {
            String folderName = folder.getName();
            File csvFile = getFileByName(files, folderName).get();
            cities.add(new City(csvFile, folder));
        }

        assert cities.size() == folders.size();

        return cities;
    }

    private Optional<File> getFileByName(Collection<File> files, String fileName) {
        String csvFileName = fileName + CSV_FILE_EXTENSION;
        return files.stream().filter(f -> f.getName().equals(csvFileName)).findAny();
    }
}
