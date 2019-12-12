package com.example.reservationapp;

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.zxing.integration.android.IntentIntegrator
import kotlinx.android.synthetic.main.activity_qr_reservation.*

class QrReservationActivity : AppCompatActivity() {

    val database = FirebaseDatabase.getInstance()
    val mAuth = FirebaseAuth.getInstance()
    var user = mAuth.currentUser
    var myRefRestaurant = database.getReference("Restaurant")
    var myRefUser = database.getReference("UserList")

    var resName : String = ""
    var userID : String = ""
    var waitNum : Int = -1
    var resurl : String = ""
    var remainingNum : Int = -1

    private lateinit var webView: WebView

    var serviceIntent : Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr_reservation)
        initScan()

        serviceIntent = Intent(this, SurveillanceService::class.java)

        // 예약 취소 시 파이어 베이스에 담겨진 대기고객 정보를 지우는 작업 수행
        cancle_reservation.setOnClickListener {
            myRefUser.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    resName = dataSnapshot.child(removeAt(userID)).child("resName").getValue(String::class.java)!!
                    waitNum = dataSnapshot.child(removeAt(userID)).child("waitNum").getValue(Int::class.java)!!
                }

                override fun onCancelled(p0: DatabaseError) {
                }
            })
            myRefUser.child(removeAt(userID)).child("waitNum").setValue(-1)
            myRefUser.child(removeAt(userID)).child("resName").setValue("")

            myRefRestaurant.child(resName).child("대기고객").child(waitNum.toString()).removeValue()
            // TODO("첫번째 대기번호 수정하는 부분")

            waitNum = -1
            resName = ""
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // 남은 대기 손님 지속적으로 확인
        myRefRestaurant.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.hasChild(resName) && !resName.isBlank()) {
                    remainingNum = waitNum - dataSnapshot.child(resName).child("NumberFront").getValue(Int::class.java)!!
                }
                show()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })
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
                //the result data is null or empty then
                finish()
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

        // QR 코드를 찍은 후 QR코드 내용 (식당이름)에 해당하는 파이어 베이스 DB 목록에 예약한 사용자의 정보를 담는 작업 수행
        myRefRestaurant.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if(dataSnapshot.hasChild(resName)){
                    waitNum = dataSnapshot.child(resName).child("NumberEnd").getValue(Int::class.java)!!
                    myRefRestaurant.child(resName).child("NumberEnd").setValue(waitNum + 1)
                    remainingNum = waitNum - dataSnapshot.child(resName).child("NumberFront").getValue(Int::class.java)!! + 1
                    resurl = dataSnapshot.child(resName).child("url").getValue(String::class.java)!!
                    ViewWeb(resurl)
                }
                else{
                    Log.d("zxcv","Error? : dataSnapshot.hasCild(resName) == false")
                }

                myRefUser.child(removeAt(userID)).child("waitNum").setValue(waitNum)
                myRefUser.child(removeAt(userID)).child("resName").setValue(resName)

                myRefRestaurant.child(resName).child("대기고객").child(waitNum.toString()).setValue(userID)

                serviceIntent!!.putExtra("resName",resName)
                serviceIntent!!.putExtra("waitNum", waitNum)
                startService(serviceIntent)

                show()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })
    }

    // 화면 처리 및 남은 대기 인원 확인
    fun show(){
        text_storename.text = resName
        text_number.text = waitNum.toString()
        text_numberNext.text = remainingNum.toString()
        if(remainingNum == 0){
            val intentCallActivity = Intent(this, CallActivity::class.java)
            intentCallActivity.putExtra("resName",resName)

            intentCallActivity.putExtra("Count","0")
            intentCallActivity.putExtra("waitNum",waitNum)

            startActivityForResult(intentCallActivity,1)
            finish()
        }
    }

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
    fun Context.toast(message : String){
        //그냥 위에 webview에서 toast에서 toast.makeText하니깐 makeText에 빨간불떠서 안되니깐 밖에서 만들어줌
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onBackPressed() {
        if(webview.canGoBack()){
            webview.goBack()
            //만약 webview.canGoBack이 참이면 webview를 한칸 뒤로 돌아갈수 있게 했음
        }else {
            super.onBackPressed()
        }
    }

    fun ViewWeb(url : String){
        val settings = webview.settings

        settings.javaScriptEnabled = true
        settings.setAppCacheEnabled(true)
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.setAppCachePath(cacheDir.path)



        settings.loadsImagesAutomatically = true
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            settings.safeBrowsingEnabled = true
        }
        settings.useWideViewPort = true
        settings.loadWithOverviewMode = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.mediaPlaybackRequiresUserGesture = false

        settings.domStorageEnabled = true
        settings.setSupportMultipleWindows(true)
        settings.loadWithOverviewMode = true
        settings.allowContentAccess = true
        settings.setGeolocationEnabled(true)
        settings.allowUniversalAccessFromFileURLs = true
        settings.allowFileAccess = true
        webview.fitsSystemWindows = true

        webview.setLayerType(View.LAYER_TYPE_HARDWARE,null)


        webview.webViewClient = object : WebViewClient(){
            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                webview.canGoBack()
            }
        }
        webview.loadUrl(url)
    }

}