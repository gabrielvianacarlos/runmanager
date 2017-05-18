package com.runmanager.gabriel.runmanager.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by gabriel on 18/05/17.
 */

public abstract class MaskUtil {

    public static final String FORMAT_DATE = "##/##/####";

    public static TextWatcher mask(final EditText ediTxt, final String mask) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            @Override
            public void afterTextChanged(final Editable s) {}

            @Override
            public void beforeTextChanged(final CharSequence s, final int start, final int count, final int after) {}

            @Override
            public void onTextChanged(final CharSequence s, final int start, final int before, final int count) {
                final String str = MaskUtil.unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (final char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (final Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }
        };
    }

    public static String unmask(final String campo) {
        return campo
                .replaceAll("[.]", "")
                .replaceAll("[-]", "")
                .replaceAll("[/]", "")
                .replaceAll("[(]", "")
                .replaceAll("[ ]","")
                .replaceAll("[)]", "");
    }
}
