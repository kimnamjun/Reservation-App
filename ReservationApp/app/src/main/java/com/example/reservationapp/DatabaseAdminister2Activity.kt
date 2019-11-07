package com.example.reservationapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reservationapp.DbStructure.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_database_administer.*
import kotlinx.android.synthetic.main.activity_database_administer2.*
import kotlinx.android.synthetic.main.activity_database_test.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class DatabaseAdminister2Activity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_administer)

        var myRef1 = database.getReference("ReservationList")
        var myRef2 = database.getReference("OrderList")

        button_admin2_btn1.setOnClickListener {
            var text11 = editText_admin2_edt11.text.toString()
            var text12 = editText_admin2_edt12.text.toString()
            var text13 = editText_admin2_edt13.text.toString()
            var text14 = editText_admin2_edt14.text.toString().toInt()
            var resInfo = ReservationInfo(text11, text12, text13, text14)
            myRef1.child(getTime()).setValue(resInfo)
        }

        button_admin2_btn2.setOnClickListener {
            var text21 = editText_admin2_edt21.text.toString()
            var text22 = editText_admin2_edt22.text.toString()
            var text23 = editText_admin2_edt23.text.toString()
            var orderInfo = OrderInfo(text21, text22, text23)
            myRef2.child(getTime()).setValue(orderInfo)
        }

        button_admin_btn_back.setOnClickListener {
            finish()
        }
    }

    private fun getTime() : String{
        return SimpleDateFormat("yyyyMMddHHmmss").format(Date(System.currentTimeMillis()))
    }
}