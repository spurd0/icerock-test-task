package com.icerockdev.babenko.model;

/**
 * Created by Roman Babenko on 30/04/17.
 */

public class DataField {
    private int id;
    private String type;
    private String placeholder;
    private String default_value;

    public int getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public String getDefault_value() {
        return default_value;
    }

    @Override
    public String toString() {
        return "Id is " + id + " type is " + type + " placeholder is " + placeholder +
                " default_value is " + default_value;
    }
}
