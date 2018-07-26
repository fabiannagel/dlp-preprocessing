package com.dlp.BoundingBox;

import com.dlp.DataSetConstants;
import com.dlp.MotorbikeLabel;
import com.opencsv.bean.CsvBindByName;

public class BoundingBox {

    //@CsvBindByName(column = "", required = true)
    //@CsvBindByPosition(position = 0)
    //private String empty;

    @CsvBindByName(column = "eid", required = true)
    private String eid;

    @CsvBindByName(column = "fid", required = true)
    private int fid;

    @CsvBindByName(column = "label", required = true)
    private String label;

    // bounding box information

    @CsvBindByName(column = "x", required = true)
    private int bounding_box_x;

    @CsvBindByName(column = "y", required = true)
    private int bounding_box_y;

    @CsvBindByName(column = "w", required = true)
    private int bounding_box_width;

    @CsvBindByName(column = "h", required = true)
    private int bounding_box_height;


    // Getters

    public String getEid() {
        return eid;
    }

    public int getFid() {
        return fid;
    }

    public MotorbikeLabel getLabel() {
        return MotorbikeLabel.fromIdentifier(this.label);
    }

    public int getBounding_box_x() {
        return bounding_box_x;
    }

    public int getBounding_box_y() {
        return bounding_box_y;
    }

    public int getBounding_box_width() {
        return bounding_box_width;
    }

    public int getBounding_box_height() {
        return bounding_box_height;
    }

    @Override
    public String toString() {
        return "BoundingBox{" +
                "eid='" + eid + '\'' +
                ", fid='" + fid + '\'' +
                ", bounding_box_height=" + bounding_box_height +
                ", label=" + label +
                ", bounding_box_width=" + bounding_box_width +
                ", bounding_box_x=" + bounding_box_x +
                ", bounding_box_y=" + bounding_box_y +
                '}';
    }

    private float computeRelativeCoordinate(int absoluteValue, int fraction) {
        return absoluteValue / fraction;
    }

    // returns the information of this bounding box in the way darknet needs it
    public String getDarknetRepresentation() {
        String separator = " ";

        String objectClass = String.valueOf(getLabel().getClassNumber());

        float bounding_box_x_relative = computeRelativeCoordinate(this.bounding_box_x, DataSetConstants.IMAGE_WIDTH);
        String bounding_box_x = String.valueOf(bounding_box_x_relative);

        float bounding_box_y_relative = computeRelativeCoordinate(this.bounding_box_y, DataSetConstants.IMAGE_HEIGHT);
        String bounding_box_y = String.valueOf(bounding_box_y_relative);

        float bounding_box_w_relative = computeRelativeCoordinate(this.bounding_box_width, DataSetConstants.IMAGE_WIDTH);
        String bounding_box_width = String.valueOf(bounding_box_w_relative);

        float bounding_box_h_relative = computeRelativeCoordinate(this.bounding_box_height, DataSetConstants.HEIGHT);
        String bounding_box_height = String.valueOf(bounding_box_h_relative);

        String darknetRepresentation = objectClass
                + separator
                + bounding_box_x
                + separator
                + bounding_box_y
                + separator
                + bounding_box_width
                + separator
                + bounding_box_height;

        return darknetRepresentation;
    }
}
