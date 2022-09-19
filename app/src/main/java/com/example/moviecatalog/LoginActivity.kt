package com.example.moviecatalog

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = Firebase.auth

        findViewById<Button>(R.id.btnLogin).setOnClickListener { signIn() }

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser == null) return
        Toast.makeText(this, "Already authenticated.", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, MovieListActivity::class.java))
        finish()
    }

    private fun signIn() {
        val email = findViewById<TextView>(R.id.txtEditEmail).text.toString()
        val password = findViewById<TextView>(R.id.txtEditPassword).text.toString()

        if (email == "" || password == "") {
            Toast.makeText(this, "Make sure to enter an email and password.",
                Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Welcome back!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MovieListActivity::class.java))
                    finish()
                }
                else {
                    Toast.makeText(this, "Failed to authenticate. Please try again.",
                        Toast.LENGTH_LONG).show()
                }

            }
    }
}