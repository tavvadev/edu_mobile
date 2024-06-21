package com.jds.eduTech.beans;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class TextValueUnits {

    @SerializedName("text")
    private String text;

    @SerializedName("value")
    private String value;

    @SerializedName("units")
    private String units;

    public TextValueUnits() { }

    public TextValueUnits(String text, String value,String units) {
        this.text = text;
        this.value = value;
        this.units = units;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    @NotNull
    @Override
    public String toString() {
        return "TextValue{" +
                "text='" + text + '\'' +
                ", value='" + value + '\'' +
                ", units='" + units + '\'' +
                '}';
    }
}
