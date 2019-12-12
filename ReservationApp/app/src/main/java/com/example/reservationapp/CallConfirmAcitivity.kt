package com.example.reservationapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_call_confirm_acitivity.*
import kotlinx.android.synthetic.main.list_menu.*
import kotlinx.android.synthetic.main.list_menu.text_foodname
import kotlinx.android.synthetic.main.list_menu.text_price
import kotlinx.android.synthetic.main.list_option.*

class CallConfirmAcitivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    var RefRestaurant = database.getReference("Restaurant")

    val menuarray by lazy { intent.extras?.get("Menu") as Calllist}
    val listarray by lazy{intent.extras?.get("list") as ArrayList<OptionList>}
    val totalcount :String by lazy { intent.extras?.get("totalcount").toString()}
    val resName :String by lazy { intent.extras?.get("resName").toString()}
    var BasketArrayList = arrayListOf<basketlist>()
    //val basket by lazy { intent.extras?.get("basketlist") as ArrayList<basketlist> }
    val waitNum by lazy { intent.extras?.get("waitNum").toString() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_confirm_acitivity)
        text_foodname.text = menuarray.foodname
        text_price.text = menuarray.price
        window.statusBarColor = Color.parseColor("#312C29")

        val optionlist : ListView= findViewById(R.id.calllist_option)
        val adapter = OptionAdapter(this, listarray)
        optionlist.adapter = adapter
        val price : Int =Integer.parseInt(menuarray.price )
        var count :Int = 0
        bt_주문함.setOnClickListener{
            //바로 firebase에다가 주문한게 들어가기
            Toast.makeText(this, "주문함에 담겼습니다.", Toast.LENGTH_LONG).show()

            //var foodname: String, var option: String?, var callnumber: Int, var price: Int, var foodphoto: String?

            // 사용자가 주문한 목록들을 파이어 베이스에 저장하는 작업 수행
            RefRestaurant.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(dataSnapshot : DataSnapshot) {
                    var orderlist : String = ""

                    if(dataSnapshot.child(resName).child("주문고객").child(waitNum).hasChild("주문")) {
                        orderlist = dataSnapshot.child(resName).child("주문고객").child(waitNum).child("주문").getValue(
                                String::class.java
                            )!!
                    }

                    orderlist += menuarray.getInfo() + "/"

                    for(i in listarray.indices){
                        count += listarray[i].Callnumber
                        if(listarray[i].Callnumber != 0 ){
                            orderlist += listarray[i].optionname + "," + listarray[i].Callnumber + ","
                        }
                    }

                    orderlist += "."

                    RefRestaurant.child(resName).child("주문고객").child(waitNum).child("주문").setValue(orderlist)
                }

                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })

            val intent = Intent(this, CallActivity::class.java)
            if(totalcount =="0") {
                intent.putExtra("Count", count)
                intent.putExtra("resName",resName)
                intent.putExtra("waitNum",waitNum)
                //intent.putExtra("basketlist",BasketArrayList)
                //intent.putParcelableArrayListExtra("basketlist", BasketArrayList);

            }else{
                intent.putExtra("Count", count + Integer.parseInt(totalcount))
                intent.putExtra("resName",resName)
                intent.putExtra("waitNum",waitNum)
                //intent.putParcelableArrayListExtra("basketlist", BasketArrayList);

                //intent.putExtra("basketlist",BasketArrayList)
            }
            startActivityForResult(intent,1)
            finish()
        }
        bt_pay.setOnClickListener{
            //결제 화면으로 넘어가기
            val intent = Intent(this,PaymentActivity::class.java)
            if(totalcount =="0") {
                intent.putExtra("price", price * count )
            }else{
                intent.putExtra("price", price * count + Integer.parseInt(totalcount))
            }
            startActivityForResult(intent,1)
        }
    }
}
