package com.example.dailyedu

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.dailyedu.firestore.ErrorSnackBar
import com.example.dailyedu.firestore.FireStoreClass
import com.example.dailyedu.firestore.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : ErrorSnackBar() {

    private lateinit var registerEmail: EditText
    private lateinit var registerPassword: EditText
    private lateinit var registerRepeatPassword: EditText
    private lateinit var registerName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerEmail = findViewById<EditText>(R.id.editTextRegisterEmail)
        registerPassword = findViewById<EditText>(R.id.editTextRegisterPassword)
        registerRepeatPassword = findViewById<EditText>(R.id.editTextRegisterPasswordReapet)
        registerName = findViewById<EditText>(R.id.editTextRegisterName)

        val registerButton = findViewById<Button>(R.id.buttonRegister)

        registerButton.isEnabled = true

        registerButton.setOnClickListener {
            if (registrationData()) {
                registerUser()
            }
        }

    }

    private fun registrationData(): Boolean {
        // TODO("Consider Error Snack Bar")

        val email = registerEmail.text.toString().trim()
        val password = registerPassword.text.toString().trim()
        val repeatPassword = registerRepeatPassword.text.toString().trim()

        return when {
            TextUtils.isEmpty(email) -> {
                false
            }
            !isValidEmail(email)->{
                false
            }
            TextUtils.isEmpty(password) -> {
                false
            }
            TextUtils.isEmpty(repeatPassword) -> {
                false
            }
            password != repeatPassword -> {
                false
            }
            else -> true
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailPattern = Patterns.EMAIL_ADDRESS
        return emailPattern.matcher(email).matches()
    }

    private fun registerUser() {
        if (registrationData()) {
           // login = email used for registration
            val login: String = registerEmail.text.toString().trim(' ')
            // password chosen in registration
            val password: String = registerPassword.text.toString().trim(' ')
            // name according to registered Name
            val name: String = registerName.text.toString().trim(' ')
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(login, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->
                        if (task.isSuccessful) {
                            val firebaseUser: FirebaseUser = task.result!!.user!!
                            showErrorSnackBar(
                                "You are registered successfully. Your user id is ${firebaseUser.uid}",
                                false
                            )

                            val user = User(
                                "ID",
                                name,
                                true,
                                login,
                            )
                            FireStoreClass().registerUserFS(this@RegisterActivity, user)
                            // FirebaseAuth.getInstance().signOut() // Remove this line
                            finish()
                        } else {
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }
                    }
                )
        }
    }

    fun userRegistrationSuccess() {
        Toast.makeText(
            this@RegisterActivity, resources.getString(R.string.register_success),
            Toast.LENGTH_LONG
        ).show()
    }

}
