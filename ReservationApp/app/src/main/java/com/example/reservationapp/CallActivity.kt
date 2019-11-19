package com.example.reservationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.reservationapp.DbStructure.ReservationInfo
import com.example.reservationapp.DbStructure.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_call.*
import kotlinx.android.synthetic.main.activity_call.cancle_reservation
import kotlinx.android.synthetic.main.activity_call.text_storename
import kotlinx.android.synthetic.main.activity_qr_reservation.*

class CallActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val mAuth = FirebaseAuth.getInstance()
    var user = mAuth.currentUser
    // 나중에 얘는 order로 교체해야 됨
    var myRefUser = database.getReference("UserList")
    var userID = ""

    var CallArrayList = arrayListOf<Calllist>(
        Calllist("돈까스","6000"),
        Calllist("치즈 돈까스","7000") ,
        Calllist("고구마 돈까스","7000"),
        Calllist("대왕 돈까스","8000"),
        Calllist("까르보나라 돈까스","7000"),
        Calllist("어린이 돈까스","5000")
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        user = mAuth.currentUser
        if(user != null && user!!.email != null){
            userID = user!!.email!!
        }
        else{
            userID = "No User"
        }

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

        myRefUser.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                text_storename.text = dataSnapshot.child(removeAt(userID)).child("resName").getValue(String::class.java)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })

        cancle_reservation.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        myRefUser.child("DataChangeEvent").setValue(0)
        myRefUser.child("DataChangeEvent").removeValue()
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

    // 이메일에서 @를 없애고 AT으로 교체, .을 없애고 DOT으로 교체
    fun removeAt(userID : String) : String{
        var returnString = ""
        for((index, value) in userID.withIndex()){
            if(value != '@' && value != '.'){
                returnString += value
            }
            else if(value == '.'){
                returnString += "DOT"
            }
            else{
                returnString += "AT"
            }
        }
        return returnString
    }
}



