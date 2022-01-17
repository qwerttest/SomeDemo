package com.example.customspan

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.text.TextPaint
import android.text.style.ImageSpan

/**
 * Des
 * @author WangJian on 2021/4/21 20
 * */
class CustomImageSpan(context: Context, bitmap: Bitmap) : ImageSpan(context, bitmap) {

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        super.draw(canvas, text, start, end, x, top, y, bottom, paint)
        canvas.drawText("1", 0, 0, 10f, 10f, paint)
    }

    override fun updateMeasureState(p: TextPaint) {
        super.updateMeasureState(p)
    }


}