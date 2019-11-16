package com.example.reservationapp

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_database_test.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class DatabaseTestActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val mAuth = FirebaseAuth.getInstance()

    var resName : String = "resName"
    var userID : String = "userID"
    var menuName : String = "menuNAme"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_database_test)

       /* var myRefExam = database.getReference("UserList")
        myRefExam.child("20191107143502").getValue(String::class.java);
        for((index,value) in myRefExam.child) {
        }

        Toast.makeText(baseContext, "", Toast.LENGTH_SHORT).show()
*/

        //userInfo는 정보를 담고 toString 한 거
        var myRef = database.getReference("OrderList")
        var count = myRef.child("count").key?:"-1"

        var user = mAuth.currentUser

        // QR 코드를 찍으면 실행되는 함수라고 가정한다.
        button_DB_submit.setOnClickListener{

        }

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var orderInfoNum = dataSnapshot.childrenCount
                var orderInfoList = dataSnapshot.children
                var wantedID = "storypass@naver.com"
                var iter = orderInfoList.iterator()

                for(iter in orderInfoList){
                    if(iter.child("userID").getValue(String::class.java) == wantedID){
                        var temp2 = iter.child("menuName").getValue(String::class.java)
                        textView_info1.text = wantedID
                        textView_info2.text = temp2
                    }
                }

//                var orderInfo = dataSnapshot.child("20191107191851").child("userID").getValue(String::class.java)
//                textView_info1.text = orderInfo
//                textView_info2.text = ""
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })

        // 191113 바꾸기 전에 백업용
//        myRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                userInfo = dataSnapshot.child("UserInfo01").getValue(String::class.java)?:"User Info toString"
//
//                textView_userID.text = userID
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                // Failed to read value
//                Log.w("zxcv", "Failed to read value.", error.toException())
//            }
//        })

    }

    private fun getTime() : String{
        return SimpleDateFormat("yyyyMMddHHmmss").format(Date(System.currentTimeMillis()))
    }

    companion object {
        private const val TAG = "DatabaseTestActivity"
    }
}