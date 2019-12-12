package com.example.reservationapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import org.w3c.dom.Text

var storageRef : StorageReference = FirebaseStorage.getInstance("gs://reservation-system-4c150.appspot.com").getReference()

class ListAdapter(val context :Context, val calllist:ArrayList<Calllist>, val resName : String) :BaseAdapter(){
    override fun getCount(): Int {
        return calllist.size
    }

    override fun getItem(idx: Int): Any {
            return calllist[idx]

    }

    override fun getItemId(idx: Int): Long {
        return 0
    }

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View? {
        val view = LayoutInflater.from(context).inflate(R.layout.list_menu, parent,false) as View
        view.findViewById<TextView>(R.id.text_foodname).text = calllist[idx].foodname
        view.findViewById<TextView>(R.id.text_price).text = calllist[idx].price.toString()
        //val resourceId = context.resources.getIdentifier(calllist[idx].foodphoto,"drawable",context.packageName)
        //view.findViewById<ImageView>(R.id.image_view).setImageResource(resourceId)

        val imageview : ImageView = view.findViewById<ImageView>(R.id.image_view) as ImageView

        Log.d("zxcv",calllist[idx].foodphoto)

        // 파이어 베이스 스토리지에 저장된 메뉴 사진들을 Glide를 이용하여 불러오는 작업 수행
        storageRef.child("images/" + resName + "/" + calllist[idx].foodphoto + ".jpg").getDownloadUrl().addOnSuccessListener(
            OnSuccessListener<Any?> { uri ->
            Glide.with(context).load(uri).into(imageview)

            Log.d("Test", " Success!")
        }).addOnFailureListener(OnFailureListener { Log.d("Test", " Failed!") })

        //resourceId로 해가지고 foodphoto에서 가지고온 string과 똑같은 drawble에 있는 사진을 대입 시키기
            return view
    }
}