package com.jds.eduTech.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.jds.eduTech.Utils;
import com.jds.eduTech.ValidationEntry;

import java.util.List;

public class EditTextChangedListener implements TextWatcher {

    private EditText editText;
    private List<ValidationEntry> validationEntries;
    private ChangeListener listener;

    public EditTextChangedListener(EditText editText, List<ValidationEntry> validationEntries) {
        this.editText = editText;
        this.validationEntries = validationEntries;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        Boolean showError = (Boolean) editText.getTag();
        if(listener != null) listener.onChange(s.toString());
        if(showError != null && showError) {
            String error = Utils.validate(s.toString(), validationEntries);
//            CharSequence previousError = editText.getError();
//            if(previousError == null && error != null || previousError != null && !previousError.toString().equals(error)) {
                editText.setError(error);
//            }
        }
    }

    public void showError() {
        String error = Utils.validate(editText.getText().toString(), validationEntries);
//        CharSequence previousError = editText.getError();
//        if(previousError == null && error != null || previousError != null && !previousError.toString().equals(error)) {
            editText.setError(error);
//        }
    }

    public void setChangeListener(ChangeListener listener) {
        this.listener = listener;
    }

    public interface ChangeListener {
        void onChange(String value);
    }

    public boolean isValid() {
        return Utils.validate(editText.getText().toString(), validationEntries) == null;
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}
