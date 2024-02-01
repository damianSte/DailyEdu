package com.example.dailyedu

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
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
    private lateinit var goBackInToLogIn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerEmail = findViewById<EditText>(R.id.editTextRegisterEmail)
        registerPassword = findViewById<EditText>(R.id.editTextRegisterPassword)
        registerRepeatPassword = findViewById<EditText>(R.id.editTextRegisterPasswordReapet)
        registerName = findViewById<EditText>(R.id.editTextRegisterName)

        val registerButton = findViewById<Button>(R.id.buttonRegister)
        goBackInToLogIn = findViewById(R.id.buttonGoLogIn)

        registerButton.isEnabled = true
        goBackInToLogIn.isEnabled = true

        registerButton.setOnClickListener {
            if (registrationData()) {
                registerUser()

//                registerButton.setOnClickListener{
//                    val int = Intent(this, MainDisplay::class.java)
//                    startActivity(int)
//
//                }
            }
        }

        goBackInToLogIn.setOnClickListener {

            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
        }



    }

    private fun registrationData(): Boolean {
        // TODO("Consider Error Snack Bar")

        val email = registerEmail.text.toString().trim()
        val password = registerPassword.text.toString().trim()
        val repeatPassword = registerRepeatPassword.text.toString().trim()

        return when {
            TextUtils.isEmpty(email) -> {
                showErrorSnackBar("Invalid e-mail", true)
                false
            }
            !isValidEmail(email)-> {
                showErrorSnackBar("Invalid e-mail", true)
                false
            }
            TextUtils.isEmpty(password) -> {
                showErrorSnackBar(" Password must contain at least one Cpital letter", true)
                false
            }
            TextUtils.isEmpty(repeatPassword) -> {
                if(repeatPassword!=password){
                    showErrorSnackBar("Given password must be the same", true)
                }
                false
            }
            password != repeatPassword -> {
                showErrorSnackBar("Given password must be the same", true)
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
