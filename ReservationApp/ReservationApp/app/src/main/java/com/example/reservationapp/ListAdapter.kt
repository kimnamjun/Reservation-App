package com.example.reservationapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import org.w3c.dom.Text

class ListAdapter(val context :Context, val calllist:ArrayList<Calllist>) :BaseAdapter(){
    override fun getCount(): Int {
        return calllist.size
    }

    override fun getItem(idx: Int): Any {
3
            return calllist[idx]

    }

    override fun getItemId(idx: Int): Long {
        return 0
    }

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.list_menu, null)

            view.findViewById<TextView>(R.id.text_foodname).text = calllist[idx].foodname
            view.findViewById<TextView>(R.id.text_price).text = calllist[idx].price

            return view
    }
}