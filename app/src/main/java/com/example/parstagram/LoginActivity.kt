package com.example.parstagram

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.parse.ParseUser

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Check if there's a user logged in
        // If there is, take them to MainActivity
        if (ParseUser.getCurrentUser() != null) {
            goToMainActivity()
        }



        findViewById<Button>(R.id.btLogin).setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            loginUser(username,password)
        }

        findViewById<Button>(R.id.btSignup).setOnClickListener {
            val username = findViewById<EditText>(R.id.etUsername).text.toString()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()
            signUpUser(username,password)
        }
    }

    private fun signUpUser(username :String, password: String) {
        // Create the ParseUser
        val user = ParseUser()

        // Set fields for the user to be created
        user.setUsername(username)
        user.setPassword(password)

        user.signUpInBackground { e ->
            if (e == null) {
                // Successfully created a new account
                goToMainActivity()
                Toast.makeText(this, "Log in successfully!", Toast.LENGTH_SHORT). show()
            } else {
                // TODO: Show a toast to tell user sign up was not successful
                Toast.makeText(this, "Log in failed.", Toast.LENGTH_SHORT). show()
                e.printStackTrace()
            }
        }
    }


    private fun loginUser(username: String, password: String) {
        ParseUser.logInInBackground(username, password, ({ user, e ->
            if (user != null) {
                Log.i(TAG, "Successfully!")
                goToMainActivity()
            } else {
                e.printStackTrace()
                Toast.makeText(this, "Error logging in", Toast.LENGTH_SHORT).show()
            }})
        )
    }



    private fun goToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
    companion object {
        const val TAG = "LoginActivity"
    }
}