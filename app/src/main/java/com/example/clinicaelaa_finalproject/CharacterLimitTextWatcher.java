package com.example.clinicaelaa_finalproject;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class CharacterLimitTextWatcher implements TextWatcher {
    private EditText editText;
    private int maxLength;

    public CharacterLimitTextWatcher(EditText editText, int maxLength) {
        this.editText = editText;
        this.maxLength = maxLength;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // No se requiere implementación
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // No se requiere implementación
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.length() > maxLength) {
            String limitedText = s.toString().substring(0, maxLength);
            editText.setText(limitedText);
            editText.setSelection(maxLength);
        }
    }
}
