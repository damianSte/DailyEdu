package com.example.dailyedu

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class MainDisplay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_display)


        val biologyBtnView = findViewById<ImageButton>(R.id.ImageBtnBiology)
        val chemistryBtnView = findViewById<ImageButton>(R.id.ImageBtnChemistry)
        val physicsBtnView = findViewById<ImageButton>(R.id.ImageBtnPhysics)


        biologyBtnView.setOnClickListener {
            val intent = Intent(this, BiologyActivity::class.java)
            startActivity(intent)
        }

    }

}