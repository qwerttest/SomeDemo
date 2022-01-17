package com.example.bitmaprecycledemo

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import java.io.Serializable
import java.util.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val iv1 = findViewById<ImageView>(R.id.iv1)
        val iv2 = findViewById<ImageView>(R.id.iv2)
        val btn = findViewById<FloatingActionButton>(R.id.fab)

        var flag = false

        btn.setOnClickListener {
//            iv1.setImageBitmap(createBitmap())
//            iv2.setImageBitmap(createBitmap())
//            println("MEM=${getFreeMem(this)}")
//            logMem()
            val json = "{\"name\":\"aaa\"}"
            val a: A = Gson().fromJson(json, A::class.java)
            val intent = Intent(this@MainActivity, MainActivity::class.java)
            intent.putExtra("a", a)
            startActivity(intent)
        }

        val a = this.intent.getSerializableExtra("a")
        println(a)
    }

    class A : Serializable{
        var name:String? = null
        var info: List<Map<String, Any>>? = null
    }

    fun createBitmap(): Bitmap{
        val bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_launcher1)
        val bmp = Bitmap.createScaledBitmap(bitmap, 100, 100, false)
        bitmap.recycle()
        return bmp
    }

    fun getFreeMem(context: Context): Long {
        val manager = context
                .getSystemService(Activity.ACTIVITY_SERVICE) as ActivityManager
        val info = ActivityManager.MemoryInfo()
        manager.getMemoryInfo(info)
        // 单位Byte
        return info.availMem
    }

    fun logMem(){
        val M = 1024 * 1024
        val r = Runtime.getRuntime()

        println("最大可用内存:" + r.maxMemory() / M + "M")
        println("当前可用内存:" + r.totalMemory() / M + "M")
        println("当前空闲内存:" + r.freeMemory() / M + "M")
        println("当前已使用内存:" + (r.totalMemory() - r.freeMemory()) / M + "M")
    }

    fun test(){

    }
}