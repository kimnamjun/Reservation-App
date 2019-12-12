package com.example.reservationapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T

class SplashActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val handler = Handler();
        handler.postDelayed(SplashHandler(), 1000);
    }

    private inner class SplashHandler : Runnable {
        override fun run() {
            startActivity(Intent(application, MainActivity::class.java))
            this@SplashActivity.finish()
        }
    }
}