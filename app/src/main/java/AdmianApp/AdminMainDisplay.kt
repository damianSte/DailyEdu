package AdmianApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.dailyedu.R

class AdminMainDisplay : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main_display)


        val biologyBtnView = findViewById<ImageButton>(R.id.ImageBtnBiology)
        val chemistryBtnView = findViewById<ImageButton>(R.id.ImageBtnChemistry)
        val physicsBtnView = findViewById<ImageButton>(R.id.ImageBtnPhysics)


        biologyBtnView.setOnClickListener {
            val intent = Intent(this, AdminBiologyActivity::class.java)
            startActivity(intent)
        }
        chemistryBtnView.setOnClickListener {
            val intent = Intent(this, AdminChemistryActivity::class.java)
            startActivity(intent)
        }
        physicsBtnView.setOnClickListener {
            val intent = Intent(this, AdminPhysicsActivity::class.java)
            startActivity(intent)
        }

    }

}