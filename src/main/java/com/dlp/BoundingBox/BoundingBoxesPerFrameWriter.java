package com.dlp.BoundingBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

// writes all bounding boxes of the given BoundingBoxesPerFrame object into a single file.
// doesn't account for frames which don't have any existing bounding boxes
public class BoundingBoxesPerFrameWriter {

    private BoundingBoxesPerFrame boundingBoxes;
    private Path outputFilePath;

    public BoundingBoxesPerFrameWriter(BoundingBoxesPerFrame boundingBoxes, Path outputFilePath) {
        this.boundingBoxes = boundingBoxes;
        this.outputFilePath = outputFilePath;
    }

    public void writeBoundingBoxFile() throws IOException {
        File outputFile = this.outputFilePath.toFile();
        int frameId = this.boundingBoxes.getFrameId();

        for (BoundingBox box : this.boundingBoxes.getBoundingBoxes()) {



        }

    }
}
