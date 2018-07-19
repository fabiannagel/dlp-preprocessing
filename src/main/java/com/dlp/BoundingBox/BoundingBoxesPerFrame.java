package com.dlp.BoundingBox;

import java.io.File;
import java.util.List;

public class BoundingBoxesPerFrame {

    private int frameId;
    private List<BoundingBox> boundingBoxes;
    private File imageFile;     // the concrete image file for which the bounding boxes are described

    public BoundingBoxesPerFrame(int frameId, List<BoundingBox> boundingBoxes, File imageFile) {
        this.frameId = frameId;
        this.imageFile = imageFile;
        this.boundingBoxes = boundingBoxes;
    }

    public int getFrameId() {
        return frameId;
    }

    public List<BoundingBox> getBoundingBoxes() {
        return boundingBoxes;
    }

    public File getImageFile() {
        return imageFile;
    }
}
