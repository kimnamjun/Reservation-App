// 이건 안쓸 겁니다. 이게 QrReservationActivity로 변환

//package com.example.reservationapp
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.view.Menu
//import android.view.MenuInflater
//import android.view.MenuItem
//import android.widget.Toast
//import com.example.reservationapp.DbStructure.ReservationInfo
//import com.example.reservationapp.MainActivity
//import com.example.reservationapp.R
//import com.google.firebase.auth.FirebaseAuth
//import com.google.firebase.database.FirebaseDatabase
//import com.google.zxing.integration.android.IntentIntegrator
//import kotlinx.android.synthetic.main.activity_main.*
//import kotlinx.android.synthetic.main.activity_qr_reservation.*
//import java.text.SimpleDateFormat
//import java.util.*
//
//class QReserveActivity : AppCompatActivity() {
//
//    val database = FirebaseDatabase.getInstance()
//    val mAuth = FirebaseAuth.getInstance()
//    var user = mAuth.currentUser
//    var id : String = ""
//    var myRef = database.getReference("ReservationList")
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_qreserve)
//        initScan()
//    }
//
//    override fun onResume(){
//        super.onResume()
//        user = mAuth.currentUser
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        val inflater: MenuInflater = menuInflater
//        inflater.inflate(R.menu.reservemenu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//
//            R.id.action_cancle -> {
//                val intent = Intent(this, MainActivity::class.java)
//                intent.putExtra("Id","ok7624583")
//                startActivityForResult(intent,1)
//                finish()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }
//    private fun initScan() {
//        IntentIntegrator(this).initiateScan()
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
//        if (result != null) {
//            if (result.contents == null) {
//                //the result data is null or empty then/
//                Toast.makeText(this, "this data is empty", Toast.LENGTH_LONG).show()
//            } else {
//                //this error because result maybe empty so use settext
//
//                var restaurantName = result.contents.toString();
//                if (user != null) {
//                    if (user!!.email != null) {
//                        id = user!!.email!!;
//                    } else {
//                        id = "No User"
//                    }
//                } else {
//                    id = "No User"
//                }
//                var time = getTime()
//                // DB에서 현재 waitNum 받기
//                var waitNum = 0
//
//                var resInfo = ReservationInfo(restaurantName, id, time, waitNum)
//                myRef.child(getTime()).setValue(resInfo)
//
//                text_number.text = result.contents.toString()
//            }
//
//        } else {
//            //the camera will not close if the result is still null
//            super.onActivityResult(requestCode, resultCode, data)
//        }
//    }
//
//
//
//    private fun getTime() : String{
//        return SimpleDateFormat("yyyyMMddHHmmss").format(Date(System.currentTimeMillis()))
//    }
//}