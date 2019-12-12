package com.example.reservationapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

//var storageRef : StorageReference = FirebaseStorage.getInstance("gs://reservation-system-4c150.appspot.com").getReference()

class BasketAdapter(val context :Context, val basket : ArrayList<basketlist>,val resName : String ) :BaseAdapter(){
    override fun getCount(): Int {
        return  basket.size
    }

    override fun getItem(idx: Int): Any {
        return basket[idx]
    }

    override fun getItemId(idx: Int): Long {
        return 0
    }

    override fun getView(idx: Int, convertView: View?, parent: ViewGroup?): View {
        val view = LayoutInflater.from(context).inflate(R.layout.basklist_layout, parent,false) as View
        view.findViewById<TextView>(R.id.text_food_name).text = basket[idx].foodname
        view.findViewById<TextView>(R.id.text_price).text = basket[idx].price.toString()
        view.findViewById<TextView>(R.id.text_option).text = basket[idx].option + "(" + basket[idx].callnumber.toString() + ")"
        //view.findViewById<TextView>(R.id.text_allprice).text = (basket[idx].callnumber * basket[idx].price).toString()

        //view.findViewById<TextView>(R.id.text_option).text = basket[idx].option.toString()

        // view.findViewById<ImageView>(R.id.image_view).setImageResource(R.drawable.don)


        val imageview : ImageView = view.findViewById<ImageView>(R.id.image_view) as ImageView

        // 파이어 베이스 스토리지에서 저장된 메뉴 사진을 불러오는 작업 수행
        storageRef.child("images/" + resName + "/" + basket[idx].foodphoto + ".jpg").getDownloadUrl().addOnSuccessListener(
            OnSuccessListener<Any?> { uri ->
                Glide.with(context).load(uri).into(imageview)

                Log.d("Test", " Success!")
            }).addOnFailureListener(OnFailureListener { Log.d("Test", " Failed!") })

        /*
        val plus = view.findViewById<ImageButton>(R.id.bt_plus)
        plus.setOnClickListener {
            basket[idx].callnumber += 1
            view.findViewById<TextView>(R.id.text_callnumber).text =basket[idx].callnumber.toString()
            //이게 중복되어 있다고 해서
        }
        val minus = view.findViewById<ImageButton>(R.id.bt_minus)
        minus.setOnClickListener {
            if(basket[idx].callnumber >0) {
                basket[idx].callnumber -= 1
            }
            view.findViewById<TextView>(R.id.text_callnumber).text =basket[idx].callnumber.toString()
        }
         */

        return view
    }
}
