package com.example.reservationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_call.*

class CallActivity : AppCompatActivity() {

    var CallArrayList = arrayListOf<Calllist>(
        Calllist("치킨","10000"),
        Calllist("피자","12000") ,
        Calllist("떡볶이","5000"),
        Calllist("순대","3000"),
        Calllist("튀김","2000"),
        Calllist("오뎅","2000")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)


        //firebase에서 하나씩 올때마다 차례차례 list에 집어넣기
        val list :ListView = findViewById(R.id.list_call)
        val adapter = ListAdapter(this, CallArrayList)
        list.adapter = adapter

        list.setOnItemClickListener()
        {parent,itemView,postion,id->
            val intent  = Intent(this, CallConfirmAcitivity::class.java)
            intent.putExtra("Menu", CallArrayList[postion])

            //그냥 적으면 CallArrayList가 그냥 보낼수있는 형식이 아니여서 error뜸 parceable만들어줘야함
            startActivityForResult(intent,1)
        }
    }

/*   도저히 안된다 나중에 다시생각, callactivity에서 음식이름과 가격 중간에 옵션에 관해서 textview를 넣을려고 햇는데 잘안된다
  override fun toString(): String {
        val count = 0
        var foodarray = arrayOf("0")
        //callactivity에서 listview만들때 중간에 글자가 이상한게 찍혀서 toString재상속 받아야할듯
        while(count> OptionArrayList.size){

        }
        return foodarray
    }
    */

}



