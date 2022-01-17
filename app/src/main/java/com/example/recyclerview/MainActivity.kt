package com.example.recyclerview

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bitmaprecycledemo.R

/**
 * Des 测试recyclerView的拖动及系统异常问题
 * @author WangJian on 2021/7/20 15
 * */
class MainActivity: AppCompatActivity() {
    val adapter = MyAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)

        val rv = findViewById<RecyclerView>(R.id.rv)

        rv.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        rv.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(ItemDragCallback(adapter, adapter.list))
        itemTouchHelper.attachToRecyclerView(rv)
    }

    fun reset(view: View) {
        val list = mutableListOf<String>()
        for(i in 0..30){
            list.add("$i")
        }
        adapter.resetData(list)
    }
}