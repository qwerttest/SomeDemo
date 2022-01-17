package com.example.span

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.style.ImageSpan

/**
 * Des
 * @author WangJian on 2021/5/14 14
 * */
class CImageSpan(drawable: Drawable) : ImageSpan(drawable) {

    var textboundhight: Float = 0f
    var textY: Float = 0f

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {

        //获取需要设置样式的字符串
        val str = text?.subSequence(start, end)?.toString()
        str?.let {
            val bounds = Rect()
            paint.textSize = 20f
            paint.getTextBounds(str, 0, str.length, bounds)
            val textHeight = bounds.height()
            val textWidth = bounds.width()
            drawable.setBounds(0, (top * 1.3).toInt(), (textWidth * 1.3).toInt(), bottom)

            val bounds1 = drawable.bounds
            //根据背景图算出 字符串居中绘制的位置
            val textX = x + bounds1.width() / 2 - bounds.width() / 2;
            if (textboundhight == 0f) {
                textboundhight = bounds.height().toFloat();
                textY = (bounds1.height()) / 2 + textboundhight / 2 ;
            }
            //绘制字符串
            canvas.drawText(str, textX, textY, paint);
        }

    }
}