package com.example.reservationapp;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.reservationapp.DbStructure.ResInfo
import com.example.reservationapp.DbStructure.ReservationInfo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_database_test.*
import kotlinx.android.synthetic.main.activity_qr_reservation.*
import java.text.SimpleDateFormat
import java.util.*

class QrReservationActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val mAuth = FirebaseAuth.getInstance()
    var user = mAuth.currentUser
    var myRefReservation = database.getReference("ReservationList")

    var resName : String = ""
    var userID : String = ""
    var waitNum : Int = -1
    var resTime : String = ""


    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_reservation)
        initScan()

        button_control.setOnClickListener {
            Log.d("zxcv", "강제 실행")
            afterLoadDataFromQrCode("뜨끈한 국밥집")
        }

        // 예약 Ref
        myRefReservation.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var reservationInfoList = dataSnapshot.children

                for(iter in reservationInfoList){
                    if(iter.hasChild("userID") && iter.child("userID").getValue(String::class.java) == userID){

                        if(iter.child("waitNum").getValue(Int::class.java) == -1){


                            // waitNum은 받아와야 됨
                            var reservationInfo = ReservationInfo(resName,userID,resTime,waitNum)
                            myRefReservation.child(resTime).setValue(reservationInfo)
                        }
                    }
                }
                show()
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })

        webView = findViewById(R.id.webview)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        webView.loadUrl("https://store.naver.com/restaurants/detail?id=35832273")
    }

    override fun onResume(){
        super.onResume()
        user = mAuth.currentUser
        if(user != null && user!!.email != null){
            userID = user!!.email!!
        }
        else{
            userID = "No User"
        }
    }

    private fun initScan() {
        IntentIntegrator(this).initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                //the result data is null or empty then/
                Toast.makeText(this, "this data is empty", Toast.LENGTH_LONG).show()
            } else {
                //this error because result maybe empty so use settext
                afterLoadDataFromQrCode(result.contents.toString())
            }
        } else {
            //the camera will not close if the result is still null
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun afterLoadDataFromQrCode(qrResName : String){
        resName = qrResName
        resTime = getTime()

        // TODO 시작
        // 1. 식당에서 waitNum을 받아 온다..
        // 2. 받아온 값에 1을 더한다.
        // 3. 그 값을 식당 리스트(DB)에 수정한다.
        // 4. 받아온 waitNum 값을 포함하여 예약 리스트(DB)에 추가한다.

        // onDataChange에서 사용하는 것은 좋지 않아보임
        // onDataChange말고도 값을 불러오는 방법이 있었으면 좋겠음
        // child 값에 시간 정보 말고 다른 것을 넣어봐야 되나?
        // TODO 끝

//        var reservationInfo = ReservationInfo(resName, userID, resTime)
//
//        // 대기 번호 없는 정보를 날리면 onDataChange에서 받아올거임
//        myRefReservation.child(resTime).setValue(reservationInfo)

        // 위에꺼 잘되면 이거 뺄 예정
        show()
    }

    fun show(){
        text_storename.text = resName
        text_number.text = waitNum.toString()
    }

    private fun getTime() : String{
        return SimpleDateFormat("yyyyMMddHHmmss").format(Date(System.currentTimeMillis()))
    }
}
