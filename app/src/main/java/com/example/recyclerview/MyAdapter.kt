package com.example.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bitmaprecycledemo.R

/**
 * Des
 * @author WangJian on 2021/7/20 15
 * */
class MyAdapter: RecyclerView.Adapter<MyAdapter.MViewHolder>() {
    val list = mutableListOf<String>()


    class MViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val iv = itemView.findViewById<ImageView>(R.id.iv)
        val tv = itemView.findViewById<TextView>(R.id.tv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_recyclerview_item, parent, false)
        return MViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: MViewHolder, position: Int) {
        holder.iv.setImageResource(R.drawable.ic_launcher1)
        holder.tv.text = list[position]
        holder.iv.setOnClickListener{

        }
    }

    fun resetData(data: MutableList<String>){
        list.clear()
        notifyDataSetChanged()
        list.addAll(data)
        notifyDataSetChanged()
    }
}