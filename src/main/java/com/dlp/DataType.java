package com.dlp;

public enum DataType {
    TRAINING("training_set"), VALIDATION("val_set");

    private String subFolderName;

    private DataType(String subFolderName) {
        this.subFolderName = subFolderName;
    }

    public String getSubFolderName() {
        return this.subFolderName;
    }
}
