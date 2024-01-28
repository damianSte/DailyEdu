package AdmianApp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dailyedu.R
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.toBitmap
import com.example.dailyedu.subjects.BiologyActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.io.File
import java.io.FileOutputStream


class AdminBiologyActivity : AppCompatActivity() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()


    private lateinit var editableImageView: ImageView
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Handle the selected image URI here
        editableImageView.setImageURI(uri)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_biology)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editableImageView = findViewById(R.id.editableImageView)

        // Set up the button to trigger image selection
        val selectImageButton: Button = findViewById(R.id.selectImageButton)
        selectImageButton.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        // Launch the image picker activity
        getContent.launch("image/*")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
            R.id.menu_save -> {
                addImageAndText()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun addImageAndText() {
        // Get the image URI from the ImageView
        val imageUri: Uri = bitmapToUri(editableImageView.drawable.toBitmap())

        // Generate a unique name for the image file in Firebase Storage
        val imageName = "biology_image_${System.currentTimeMillis()}.jpg"

        // Get a reference to the Firebase Storage location
        val storageReference =
            FirebaseStorage.getInstance().reference.child("biology_images").child(imageName)

        // Upload the image to Firebase Storage
        storageReference.putFile(imageUri!!)
            .addOnSuccessListener { taskSnapshot ->
                storageReference.downloadUrl.addOnSuccessListener { downloadUri ->
                    saveDataToFirestore(downloadUri.toString(), "YourTextDataHere")
                }
            }
            .addOnFailureListener { exception ->
                // Handle any errors during the image upload
                // You may want to show an error message to the user
            }
    }


    private fun saveDataToFirestore(imageUrl: String, textData: String) {
        // Get a reference to the Firestore collection where you want to store the data
        val firestoreReference = FirebaseFirestore.getInstance().collection("biology_collection")

        // Create a data object with the image URL and text data
        val data = hashMapOf(
            "image_url" to imageUrl,
            "text_data" to textData
        )

        // Add the data to Firestore
        firestoreReference.add(data)
            .addOnSuccessListener { documentReference ->
                documentReference.addSnapshotListener { snapshot, exception ->
                    if (exception != null) {
                        // Handle errors
                        return@addSnapshotListener
                    }
                    if (snapshot != null && snapshot.exists()) {
                        val imageUrlFromFirestore = snapshot.getString("image_url")
                        val textDataFromFirestore = snapshot.getString("text_data")

                    }
                }
            }
            .addOnFailureListener { exception ->

            }
    }
    private fun bitmapToUri(bitmap: Bitmap): Uri {
        // Convert Bitmap to Uri without saving to a file
        return Uri.fromFile(File.createTempFile("temp_image", ".jpg", cacheDir)).apply {
            val outputStream = FileOutputStream(this.path)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()
        }

    }

}