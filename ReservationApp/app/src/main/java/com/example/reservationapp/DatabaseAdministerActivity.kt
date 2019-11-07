package com.example.reservationapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.reservationapp.DbStructure.MenuInfo
import com.example.reservationapp.DbStructure.ResInfo
import com.example.reservationapp.DbStructure.UserInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_database_administer.*
import kotlinx.android.synthetic.main.activity_database_test.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class DatabaseAdministerActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_administer)

        var myRef1 = database.getReference("RestaurantList")
        var myRef2 = database.getReference("MenuList")
        var myRef3 = database.getReference("RestaurantList")

        button_admin_btn1.setOnClickListener {
            var text11 = editText_admin_edt11.text.toString()
            var text12 = editText_admin_edt12.text.toString().toInt()
            var resInfo = ResInfo(text11, text12)
            myRef1.child(getTime()).setValue(resInfo)
        }

        button_admin_btn2.setOnClickListener {
            var text21 = editText_admin_edt21.text.toString()
            var text22 = editText_admin_edt22.text.toString()
            var text23 = editText_admin_edt23.text.toString().toInt()
            var menuInfo = MenuInfo(text21, text22, text23)
            myRef2.child(getTime()).setValue(menuInfo)
        }

        button_admin_btn3.setOnClickListener {
            var text31 = editText_admin_edt11.text.toString()
            var text32 = editText_admin_edt12.text.toString()
            var userInfo = UserInfo(text31, text32)
            myRef3.child(getTime()).setValue(userInfo)
        }

        button_admin_btn_back.setOnClickListener {
            finish()
        }
    }

    private fun getTime() : String{
        return SimpleDateFormat("yyyyMMddHHmmss").format(Date(System.currentTimeMillis()))
    }
}