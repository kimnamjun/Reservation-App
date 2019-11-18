package com.example.reservationapp

import android.content.Context
import android.graphics.Path
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.TextView

class OptionAdapter(val context : Context, val OptionList :ArrayList<OptionList>) : BaseAdapter(){
    override fun getCount(): Int {
        return OptionList.size
    }

    override fun getItem(idx: Int): Any {

        return OptionList[idx]

    }

    override fun getItemId(idx: Int): Long {
        return 0
    }

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.list_option, null)
        view.findViewById<TextView>(R.id.text_option).text = OptionList[idx].optionname


        return view
    }
}