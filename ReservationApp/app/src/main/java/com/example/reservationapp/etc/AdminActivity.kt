package com.example.reservationapp.etc

import com.example.reservationapp.R
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_admin.*

class AdminActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("Restaurant")

    var resName = ""
    var frontNum = -1
    var endNum = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        button_admin_button.setOnClickListener {
            resName = editText_admin_name.text.toString()
            if(resName.isBlank()){
                resName = " "
            }

            myRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if(dataSnapshot.hasChild(resName)){
                        frontNum = dataSnapshot.child(resName).child("NumberFront").getValue(Int::class.java)!! + 1
                        endNum = dataSnapshot.child(resName).child("NumberEnd").getValue(Int::class.java)!!

                        if(frontNum < endNum){
                            myRef.child(resName).child("NumberFront").setValue(frontNum)
                            myRef.child(resName).child("대기고객").child(frontNum.toString()).removeValue()
                        }
                    }
                    else{
                        Log.d("zxcv","Error? : dataSnapshot.hasCild(resName) == false")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("zxcv", "Failed to read value.", error.toException())
                }
            })

            myRef.child("DataChange").setValue(0)
            myRef.child("DataChange").removeValue()
        }
    }
}