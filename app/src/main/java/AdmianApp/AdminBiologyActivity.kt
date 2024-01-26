package AdmianApp

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


class AdminBiologyActivity : AppCompatActivity() {

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
                saveImageAndText()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun saveImageAndText() {
       //TODO(add Firebase to transfer imiges there)//
        Toast.makeText(this, "Image and text saved successfully", Toast.LENGTH_SHORT).show()
    }

}