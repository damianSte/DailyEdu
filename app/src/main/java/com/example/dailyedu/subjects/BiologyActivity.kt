package com.example.dailyedu.subjects

import Adapters.Adapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.dailyedu.R
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.dailyedu.firestore.dataSubjects
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class BiologyActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: Adapter
    private lateinit var firestoreListener: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_biology)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize RecyclerView and adapter
        val recyclerView: RecyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter()
        recyclerView.adapter = adapter

        // Fetch and display data from Firestore
        fetchDataFromFirestore()
    }

    private fun fetchDataFromFirestore() {
        firestoreListener = firestore.collection("biology_collection")
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                val biologyItems = mutableListOf<dataSubjects>()
                querySnapshot?.documents?.forEach { document ->
                    val imageUrl = document.getString("image_url") ?: ""
                    val textData = document.getString("text_data") ?: ""
                    biologyItems.add(dataSubjects(imageUrl, textData))
                }

                adapter.setData(biologyItems)
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        firestoreListener.remove()
    }
}
