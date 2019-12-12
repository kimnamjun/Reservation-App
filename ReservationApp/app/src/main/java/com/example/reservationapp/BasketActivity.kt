package com.example.reservationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class BasketActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    var RefRestaurant = database.getReference("Restaurant")

    //val basket by lazy { intent.extras?.get("basket") as ArrayList<basketlist> }
    val resName :String by lazy { intent.extras?.get("resname").toString()}
    val waitNum :String by lazy { intent.extras?.get("waitNum").toString()}
    var BasketArrayList = arrayListOf<basketlist>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basket)
        //val BasketArrayList by lazy { intent.extras?.get("basket") as ArrayList<basketlist> }
        val list : ListView = findViewById(R.id.basket_list)
        val adapter = BasketAdapter(this,BasketArrayList,resName)
        list.adapter = adapter

        // 파이어 베이스에 저장된 사용자의 장바구니 목록을 불러오는 작업 수행
        RefRestaurant.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(dataSnapshot : DataSnapshot) {
                var order : String = dataSnapshot.child(resName).child("주문고객").child(waitNum).child("주문").getValue(String::class.java)!!

                var orderarray : List<String> = order.split(".")

                var FoodPrice : List<String>
                var OptionNumber : List<String>
                var detailarray : List<String>

                for(value in orderarray){
                    if(value == ("")){
                        break
                    }
                    detailarray = value.split("/")

                    FoodPrice = detailarray[0].split(",")
                    OptionNumber = detailarray[1].split(",")

                    BasketArrayList.add(
                        basketlist(
                            FoodPrice[0],
                            OptionNumber[0],
                            OptionNumber[1].toInt(),
                            FoodPrice[1].toInt(),
                            FoodPrice[2]
                        )
                    )
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}
