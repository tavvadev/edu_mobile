package com.jds.eduTech;

import com.google.gson.annotations.SerializedName;

import org.intellij.lang.annotations.Language;

public class ValidationEntry {
    @SerializedName("regExp")
    @Language("RegExp")
    private String regExp;

    @SerializedName("error")
    private String errorMessage;

    public ValidationEntry() {}

    public ValidationEntry(@Language("RegExp") String regExp, String errorMessage) {
        this.regExp = regExp;
        this.errorMessage = errorMessage;
    }

    public String getRegExp() {
        return regExp;
    }

    public void setRegExp(@Language("RegExp") String regExp) {
        this.regExp = regExp;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String validate(String value) {
        return Utils.isValid(regExp, value) ? null: errorMessage;
    }

    @Override
    public String toString() {
        return "ValidationEntry{" +
                "regExp='" + regExp + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
