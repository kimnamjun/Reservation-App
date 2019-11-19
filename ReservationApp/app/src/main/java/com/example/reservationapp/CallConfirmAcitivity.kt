package com.example.reservationapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_call_confirm_acitivity.*
import kotlinx.android.synthetic.main.list_menu.*
import kotlinx.android.synthetic.main.list_menu.text_foodname
import kotlinx.android.synthetic.main.list_menu.text_price
import kotlinx.android.synthetic.main.list_option.*

class CallConfirmAcitivity : AppCompatActivity() {
    var OptionArrayList = arrayListOf<OptionList>(
        //firebase에서 받았을때는 식당마다 받을꺼니깐 치킨집이면 순살 뼈, 이렇게 정해져있듯이 괜찮을듯
        //중국집이면 곱빼기이런거 추가하면 되듯이 optionlist는 가게마다 하나씩 적고 반영해도 ㄱㅊ
        //firebase에서 가져올때 되게 단순화 시키기 치킨집이면 순살,뼈라든지 짜장면집같은경우는 곱빼기 보통이라던지
        OptionList("매운맛"),
        OptionList("보통맛"),
        OptionList("순한맛")
    )
   // https://recipes4dev.tistory.com/68
    //https://recipes4dev.tistory.com/59
    val menuarray by lazy { intent.extras?.get("Menu") as Calllist}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_call_confirm_acitivity)
        text_foodname.text = menuarray.foodname
        text_price.text = menuarray.price
        val optionlist : ListView= findViewById(R.id.list_option)
        val adapter = OptionAdapter(this, OptionArrayList)
        optionlist.adapter = adapter

        /*optionlist.setOnItemClickListener(){parent,itemView,position,id->
            val check : CheckBox = findViewById(R.id.check_Box)
            check.setChecked(true)
           /* check.setChecked((parent as ListView).isItemChecked(position))
            check.setFocusable(false)
            check.setClickable(false)
*/
        }*/
    }
}
