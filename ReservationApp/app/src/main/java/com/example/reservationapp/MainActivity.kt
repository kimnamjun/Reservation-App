package com.example.reservationapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button : ImageButton = findViewById(R.id.newActivity)
        button.setBackgroundResource(R.drawable.hand)

        var button_confirm : Button = findViewById(R.id.button_confirm)

        button.setOnClickListener {
            val nextIntent : Intent = Intent(this, SubActivity::class.java)
            startActivity(nextIntent)
        }

        button_confirm.setOnClickListener {
            val nextIntent : Intent = Intent(this, Activity_confirm::class.java)
            startActivity(nextIntent)
        }
    }
}
