package com.example.humencheckworkattendance.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by administration on 2017/10/10.
 */

public class FocuseTextView extends TextView {

    public FocuseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean isFocused() {
        // TODO Auto-generated method stub
        return true;
    }
}
