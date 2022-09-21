package com.example.moviecatalog

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import com.example.moviecatalog.movie.Movie
import com.example.moviecatalog.movie.MovieListAdapter
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MovieCatalogActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private val database = Firebase.database
    private val myRef = database.getReference("movies")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_catalog)

        setSupportActionBar(findViewById(R.id.toolbar))

        auth = Firebase.auth

        // findViewById<ImageView>(R.id.btnHome).setOnClickListener { goBackToLogin() }
        // findViewById<ListView>(R.id.listMovies).setOnItemClickListener { adapterView, view, i, l -> }



        myRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val movies = ArrayList<Movie>()
                snapshot.children.forEach { child ->
                    val movie = Movie(child.key.toString(),
                        child.child("name").value.toString(),
                        child.child("year").value.toString(),
                        child.child("genre").value.toString())
                    movies.add(movie)
                }
                val movieListAdapter = MovieListAdapter(this@MovieCatalogActivity, movies)
                findViewById<ListView>(R.id.listMovies).adapter = movieListAdapter
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        goBackToLogin()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profile -> {
                // TODO("Show profile info")
                true
            }
            R.id.logout -> {
                goBackToLogin()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goBackToLogin() {
        auth.signOut()
        Toast.makeText(this, "Signed out successfully.", Toast.LENGTH_LONG).show()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}