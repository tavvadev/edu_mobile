package com.jds.eduTech.beans;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

public class TextValue {

    @SerializedName("text")
    private String text;

    @SerializedName("value")
    private String value;

    public TextValue() { }

    public TextValue(String text, String value) {
        this.text = text;
        this.value = value;
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

    @NotNull
    @Override
    public String toString() {
        return "TextValue{" +
                "text='" + text + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
