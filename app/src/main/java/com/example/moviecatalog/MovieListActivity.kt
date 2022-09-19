package com.example.moviecatalog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MovieListActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_list)

        auth = Firebase.auth

        findViewById<ImageView>(R.id.btnHome).setOnClickListener {
            goBackToLogin()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goBackToLogin()
    }

    private fun goBackToLogin() {
        auth.signOut()
        Toast.makeText(this, "Signed out successfully.", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}