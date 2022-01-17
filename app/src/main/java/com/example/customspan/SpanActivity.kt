package com.example.customspan

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bitmaprecycledemo.R
import java.lang.RuntimeException

/**
 * Des
 * @author WangJian on 2021/4/21 20
 * */
class SpanActivity : AppCompatActivity() {
    private lateinit var tv: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        try {
            super.onCreate(savedInstanceState)
        } catch (e: Exception) {
        }
        setContentView(R.layout.activity_span)
        tv = findViewById(R.id.tv)

        tv.setOnClickListener {
            throw RuntimeException("crash crash crash crash crash")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy onDestroy")
        finish()
    }
}