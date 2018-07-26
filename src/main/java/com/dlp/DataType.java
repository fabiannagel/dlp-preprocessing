package com.dlp;

public enum DataType {
    TRAINING("training_set", "training"), VALIDATION("val_set", "validation");

    private String subFolderName;   // the subfolder in which the data will be written
    private String fileSuffix;      // the suffix which will be in every output file (so we can separate between validation and training files)

    DataType(String subFolderName, String fileSuffix) {
        this.subFolderName = subFolderName;
        this.fileSuffix = fileSuffix;
    }

    public String getSubFolderName() {
        return this.subFolderName;
    }

    public String getFileSuffix() {
        return this.fileSuffix;
    }
}
