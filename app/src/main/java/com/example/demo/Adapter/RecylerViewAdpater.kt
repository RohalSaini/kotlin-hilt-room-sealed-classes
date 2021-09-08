package com.example.demo.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.example.demo.modal.Todo

class RecylerViewAdpater(var context: Context, var list:MutableList<Todo>) : RecyclerView.Adapter<com.example.demo.Adapter.RecylerViewAdpater.ViewHolder>() {

    override fun getItemId(positon: Int): Long {
        return positon.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.adapter_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //holder.description.text = list[position].description
        //holder.job.text = list[position].job
        holder.job.text = list[position].job
        holder.description.text = list[position].description
    }

    override fun getItemCount(): Int {
        return list.size
    }
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var job: TextView = view.findViewById(R.id.adpater_username)
        var description: TextView = view.findViewById(R.id.adpater_from)
    }
}