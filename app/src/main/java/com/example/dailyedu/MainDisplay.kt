package com.example.dailyedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.dailyedu.subjects.BiologyActivity
import com.example.dailyedu.subjects.ChemistryActivity
import com.example.dailyedu.subjects.PhysicsActivity

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
        chemistryBtnView.setOnClickListener {
            val intent = Intent(this, ChemistryActivity::class.java)
            startActivity(intent)
        }
        physicsBtnView.setOnClickListener {
            val intent = Intent(this, PhysicsActivity::class.java)
            startActivity(intent)
        }

    }

}