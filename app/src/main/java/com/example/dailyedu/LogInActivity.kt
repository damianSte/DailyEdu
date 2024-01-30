package com.example.dailyedu

import AdmianApp.AdminMainDisplay
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    private lateinit var inputEmail: EditText
    private lateinit var inputPassword: EditText
    private lateinit var logInButton: Button
    private lateinit var skipBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textViewRegister = findViewById<View>(R.id.textViewRegister)
        inputEmail = findViewById(R.id.editTextEmailAddress)
        inputPassword = findViewById(R.id.editTextPassword)
        logInButton = findViewById(R.id.buttonLogIn)

        // go to register user class
        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
        logInButton.setOnClickListener {
            logInRegisteredUser()
        }

    }

    private fun validateLoginDetails(): Boolean {
        // getting email and password trimming spaces
        val email = inputEmail.text.toString().trim()
        val password = inputPassword.text.toString().trim()

        return when {
            TextUtils.isEmpty(email) -> {
                //showErrorSnackBar("User not found", true)
                false
            }
            TextUtils.isEmpty(password) -> {
               // showErrorSnackBar("Invalid Password or Email", true)
                false
            }
            else -> {
               // showErrorSnackBar("Your details are valid", false)
                true
            }
        }
    }
    private fun logInRegisteredUser() {
        // validating from validateLoginDetails()
        if (validateLoginDetails()) {
            val email = inputEmail.text.toString().trim()
            val password = inputPassword.text.toString().trim()
            val adminEmail = "damianstepien1@interia.pl"

            // Validating admin
            if (email == adminEmail){
                if (password == "Damian17"){
                    goToAdminDisplay()
                    finish()
                    return
                } else {

                    return
                }
            }

            // getting email and password from Firebase
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        //showErrorSnackBar("You are logged in successfully.", false)
                        goToMainDisplay()
                        finish()
                    } else {
                       // showErrorSnackBar(task.exception?.message.toString(), true)
                    }
                }
        }
    }
    private fun goToMainDisplay() {

        val user = FirebaseAuth.getInstance().currentUser;
        val uid = user?.email.toString()
        // on click to SecondActivity
        val intent = Intent(this,MainDisplay::class.java)
        intent.putExtra("uID",uid)
        startActivity(intent)
    }
    private fun goToAdminDisplay() {
        val intent = Intent(this, AdminMainDisplay::class.java)
        startActivity(intent)
    }
}