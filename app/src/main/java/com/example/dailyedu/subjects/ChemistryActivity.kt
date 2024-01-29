package com.example.dailyedu.subjects

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import com.example.dailyedu.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso

class ChemistryActivity : AppCompatActivity() {
    private lateinit var firestore: FirebaseFirestore
    private lateinit var firestoreListener: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chemistry)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Initialize ImageView
        val imageView: ImageView = findViewById(R.id.imageViewDownload)
        val textView : TextView = findViewById(R.id.textViewChemistryContent)

        // Fetch and display data from Firestore
        fetchDataFromFirestore(imageView, textView)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchDataFromFirestore(imageView: ImageView, textView: TextView) {
        firestoreListener = firestore.collection("chemistry_collection")
            .orderBy("timestamp", Query.Direction.DESCENDING) // Replace "timestamp" with the actual field name in your Firestore documents
            .limit(1) // Limit the result to only one document (the most recent one)
            .addSnapshotListener { querySnapshot, error ->
                if (error != null) {
                    // Handle error
                    return@addSnapshotListener
                }

                querySnapshot?.documents?.firstOrNull()?.let { document ->
                    val imageUrl = document.getString("image_url") ?: ""
                    val textData = document.getString("text_data") ?: ""

                    // Load image into ImageView using Picasso or any other image loading library
                    Picasso.get().load(imageUrl).into(imageView)

                    // Set text data to TextView
                    textView.text = textData
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        firestoreListener.remove()
    }
}