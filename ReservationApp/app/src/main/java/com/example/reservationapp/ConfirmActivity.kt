package com.example.reservationapp

import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class ConfirmActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm)

        var button_cancel : Button = findViewById(R.id.button_cancel)

        button_cancel.setOnClickListener {
        }

        var button_refresh : ImageButton = findViewById(R.id.button_refresh)

        button_cancel.setOnClickListener {
        }
    }

}