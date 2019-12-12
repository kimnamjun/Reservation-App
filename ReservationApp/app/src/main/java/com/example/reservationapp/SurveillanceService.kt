package com.example.reservationapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.getIntentOld
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SurveillanceService : Service() {
    val auth = FirebaseAuth.getInstance()
    var user = auth.currentUser
    val database = FirebaseDatabase.getInstance()
    val myRefRes = database.getReference("Restaurant")
    var userID = " "
    var resName = " "
    var waitNum = -1
    var numFront = -1

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val returnIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,returnIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        if(intent!!.hasExtra("resName") && intent.hasExtra("waitNum")){
            resName = intent.getStringExtra("resName")
            waitNum = intent.getIntExtra("waitNum", -1)
        }
        if(user != null && user!!.email != null){
            userID = user!!.email!!
        }

        // 노티피케이션 설정 부분
        val channelId = "fcm_default_channel"
        var builder = NotificationCompat.Builder(this, channelId)
        builder.setSmallIcon(R.drawable.logo)
            .setContentTitle("대기 번호 확인 중")
            .setContentText("5팀 이하로 남으면 알려드립니다.")
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(channelId,"name", NotificationManager.IMPORTANCE_DEFAULT)

        notiManager.createNotificationChannel(channel)
        notiManager.notify(0, builder.build())

        myRefRes.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 남은 대기 인원 확인
                if(dataSnapshot.hasChild(resName)){
                    numFront = dataSnapshot.child(resName).child("NumberFront").getValue(Int::class.java)!!
                }
                if(waitNum - numFront <= 5){
                    val channelId = "fcm_default_channel"
                    val builder2 = NotificationCompat.Builder(applicationContext, channelId)
                    builder2.setSmallIcon(R.drawable.logo)
                        .setContentTitle(userID)
                        .setContentText("곧 입장할 예정입니다.")
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true)

                    val notiManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val channel2 = NotificationChannel(channelId,"name2", NotificationManager.IMPORTANCE_DEFAULT)

                    notiManager.createNotificationChannel(channel2)
                    notiManager.notify(0, builder2.build())

                    stopSelf()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("zxcv", "Failed to read value.", error.toException())
            }
        })

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(p0: Intent?): IBinder? {
        // 사용하지 않음
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return null
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
}