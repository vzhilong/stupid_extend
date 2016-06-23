package com.stupid.extend.android;

import android.widget.EditText;
import android.widget.TextView;

import com.stupid.extend.lang.StringUtils;

/**
 * Created by vincent on 16/6/5.
 */
public class StringViewUtils {
    public static String getString(TextView textView) {
        if (textView == null) {
            return StringUtils.EMPTY;
        }

        return textView.getText().toString();
    }

    public static String getString(EditText editText) {
        if (editText == null) {
            return StringUtils.EMPTY;
        }

        return editText.getText().toString();
    }
}
