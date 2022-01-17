package com.example.span;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.widget.TextView;

import androidx.annotation.DrawableRes;

public class SpannableStringUtils {

    public static void getLabelStyleText(
            Context context, String[] strArray
            , TextView textView, @DrawableRes int backgroudId) {
        String message = "";
        String[] strings = strArray;
        for (int i = 0; i < strings.length; i++) {
            message += strings[i];
            if (i != strings.length - 1) {
                message += " ";
            }

        }


        SpannableStringBuilder ssb = new SpannableStringBuilder(message);
        int startX = 0 ;
        int endX = 0 ;
        for (int i = 0; i < strings.length; i++) {
            int length = strings[i].length();
            int lastLength = 0;
            if (i != 0) {
                lastLength = strings[i - 1].length()-1;
            }
            endX = startX+strings[i].length();
            ssb.setSpan(new CImageSpan(context.getResources().getDrawable(backgroudId)), startX, endX, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            startX = endX+1;
        }
        textView.setText(ssb);
    }
}