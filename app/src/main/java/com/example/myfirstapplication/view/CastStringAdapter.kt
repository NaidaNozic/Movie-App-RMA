package com.example.myfirstapplication.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.data.Cast

class CastStringAdapter (list: List<Cast>) : RecyclerView.Adapter<CastStringAdapter.SimpleViewHolder>(){
    var list = list
    inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textElement = itemView.findViewById<TextView>(android.R.id.text1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(android.R.layout.simple_list_item_1, parent, false)
        return SimpleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
        holder.textElement.text=list[position].name
    }
}