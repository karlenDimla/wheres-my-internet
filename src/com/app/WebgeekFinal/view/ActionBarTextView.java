package com.app.WebgeekFinal.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import com.app.WebgeekFinal.font.Fonts;

/**
 * Created by kdimla on 8/17/14.
 */
public class ActionBarTextView extends TextView {
    public ActionBarTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public void setTypeface(Typeface typeface, int style) {
        switch (style) {
            default:
                super.setTypeface(Fonts.MONTSERRAT_REGULAR);
                break;
        }
    }
}
