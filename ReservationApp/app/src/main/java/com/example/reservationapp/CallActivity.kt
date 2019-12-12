package com.example.reservationapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_call.*
import kotlinx.android.synthetic.main.activity_call.cancle_reservation
import kotlinx.android.synthetic.main.activity_call.text_storename

class CallActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val mAuth = FirebaseAuth.getInstance()
    var user = mAuth.currentUser
    // 나중에 얘는 order로 교체해야 됨
    var myRefUser = database.getReference("UserList")
    var RefRestaurant = database.getReference("Restaurant")
    var userID = ""
    val basket by lazy { intent.extras?.get("basketlist") as ArrayList<basketlist> }//이걸가지고 장바구니 꾸미기
    val resName : String by lazy {intent.extras?.get("resName").toString()}
    val waitNum : String  by lazy { intent.extras?.get("waitNum").toString()}

    var CallArrayList = arrayListOf<Calllist>()
    var OptionArrayList = arrayListOf<OptionList>()
    val count : String  by lazy { intent.extras?.get("Count").toString()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call)

        window.statusBarColor = Color.parseColor("#312C29")


        if(count == "0"){
            text_all.text = "0"
        }else {
            text_all.text = count.toString()
        }

        user = mAuth.currentUser
        if(user != null && user!!.email != null){
            userID = user!!.email!!
        }
        else{
            userID = "No User"
        }

        //firebase에서 하나씩 올때마다 차례차례 list에 집어넣기
        val list :ListView = findViewById(R.id.list_call)
        val adapter = ListAdapter(this, CallArrayList, resName)
        list.adapter = adapter

        // 파이어 베이스의 식당 카테고리에서 메뉴목록을 가져오는 작업을 수행
        RefRestaurant.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var menu : String = dataSnapshot.child(resName).child("메뉴").getValue(String::class.java)!!

                var menuimage : String = dataSnapshot.child(resName).child("메뉴사진").getValue(String::class.java)!!
                var menuoption : String = dataSnapshot.child(resName).child("메뉴option").getValue(String::class.java)!!

                var menuarray : List<String> = menu.split(",")
                var menuimagearray : List<String> = menuimage.split(",")
                var menuoptionarray : List<String> = menuoption.split(",")
                var name : String = ""

                var i : Int = 0

                for(value in menuarray){
                    if(value.get(0).toInt() >= 49 && value.get(0).toInt() <= 57){
                        CallArrayList.add(Calllist(name, value, menuimagearray[i]))
                        i++
                    }
                    else{
                        name = value
                    }
                }

                for(value in menuoptionarray){
                    OptionArrayList.add(OptionList(value, 0))
                }
                RefRestaurant.child(resName).child("주문고객").child(waitNum).child("ID").setValue(userID)

                adapter.notifyDataSetChanged() // listview 갱신
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })
        bt_callham.setOnClickListener {

                val intent = Intent(this, BasketActivity::class.java)

                intent.putExtra("waitNum", waitNum)
                intent.putExtra("resname",resName)

                startActivityForResult(intent,3)

        }
        bt_pay.setOnClickListener {
            if( count =="0"){
                Toast.makeText(this, "주문 함에 아무것도 없습니다.", Toast.LENGTH_LONG).show()
            }else {
                val intent = Intent(this, PaymentActivity::class.java)
                intent.putExtra("price", count)
                startActivityForResult(intent, 4)
            }
        }
        list.setOnItemClickListener()
        {parent,itemView,position,id->
            val intent = Intent(this, CallConfirmAcitivity::class.java)

            intent.putExtra("Menu", CallArrayList[position])
            intent.putExtra("list",OptionArrayList)

            intent.putExtra("totalcount",count)
            intent.putExtra("resName",resName)
            intent.putExtra("waitNum",waitNum)

            if(count != "0") {
                intent.putExtra("basketlist", basket)
            }


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
        //myRefUser.child("DataChangeEvent").setValue(0)
        //myRefUser.child("DataChangeEvent").removeValue()
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



