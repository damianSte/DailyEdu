package com.example.dailyedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class BiologyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biology)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }
}