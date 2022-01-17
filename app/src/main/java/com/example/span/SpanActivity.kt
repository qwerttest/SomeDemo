package com.example.span

import android.os.Bundle
import android.text.SpannableStringBuilder
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bitmaprecycledemo.R

/**
 * Des
 * @author WangJian on 2021/5/14 14
 * */
class SpanActivity : AppCompatActivity() {
    private lateinit var tv: TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_span)
        tv = findViewById(R.id.tv)

        val strData = mutableListOf<String>()
        strData.add("图文混排")
        strData.add("图文混排")
        strData.add("图文混排")
        strData.add("图文混排")
        strData.add("图文混排")
        strData.add("2")
        strData.add("3")
        strData.add("4")
        strData.add("5")
        SpannableStringUtils.getLabelStyleText(this, strData.toTypedArray(), tv, R.drawable.ic_launcher_background)
    }

}