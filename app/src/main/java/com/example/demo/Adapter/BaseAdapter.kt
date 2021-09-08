package com.example.demo.Adapter

import android.content.Context
import android.widget.BaseAdapter

class BaseAdapterTemplate(var context: Context, var list:MutableList<com.example.demo.modal.User>) : BaseAdapter() {

    // inflate View
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View {
        val modifiedView: View =  LayoutInflater.from(context).inflate(R.layout.adapter_layout, viewGroup, false)
        // setting Values

        // Setting Position
        var name = modifiedView.findViewById<TextView>(R.id.adpater_number)
        name.text = (position +1).toString()

        // Setting Name
        var username = modifiedView.findViewById<TextView>(R.id.adpater_username) as TextView
        username.text = list[position].name

        // Setting Age as String
        var age = modifiedView.findViewById<TextView>(R.id.adpater_age) as TextView
        age.text = list[position].age.toString()

        // Setting from
        var from = modifiedView.findViewById<TextView>(R.id.adpater_from) as TextView
        from.text = list[position].from

        return modifiedView
    }

    //tell about count
    override fun getCount(): Int {
        return list.size
    }

    // tells about item
    override fun getItem(positon: Int): Any {
        return  positon
    }

    // tells about item ID
    override fun getItemId(positon: Int): Long {
        return positon.toLong()
    }

}