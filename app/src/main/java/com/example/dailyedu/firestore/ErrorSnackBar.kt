package com.example.dailyedu.firestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.dailyedu.R
import com.google.android.material.snackbar.Snackbar

open class ErrorSnackBar : AppCompatActivity() {

    fun showErrorSnackBar(message: String, errorMessage: Boolean) {
        val snackbar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
        val snackbarView = snackbar.view

        if (errorMessage) {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@ErrorSnackBar,
                    R.color.snackBarSuccessful
                )
            )
        } else {
            snackbarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@ErrorSnackBar,
                    R.color.snackBarError
                )
            )
        }
        snackbar.show()
    }
}