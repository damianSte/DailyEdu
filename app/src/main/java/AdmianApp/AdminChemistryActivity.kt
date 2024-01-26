package AdmianApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.dailyedu.R

class AdminChemistryActivity : AppCompatActivity() {

    private lateinit var editableImageView: ImageView
    private val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        // Handle the selected image URI here
        editableImageView.setImageURI(uri)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_chemistry)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        editableImageView = findViewById(R.id.editableImageView)

        // Set up the button to trigger image selection
        val selectImageButton: Button = findViewById(R.id.selectImageButton)
        selectImageButton.setOnClickListener {
            openImagePicker()
        }
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

    private fun openImagePicker() {
        // Launch the image picker activity
        getContent.launch("image/*")
    }
}