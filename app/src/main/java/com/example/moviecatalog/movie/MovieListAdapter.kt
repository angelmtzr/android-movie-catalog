package com.example.moviecatalog.movie

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.moviecatalog.R

class MovieListAdapter(private val context: Activity, private val movies: ArrayList<Movie>) :
    ArrayAdapter<Movie>(context, R.layout.movie, movies){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.movie, null)

        view.findViewById<TextView>(R.id.txtMovieName).text = movies[position].name
        view.findViewById<TextView>(R.id.txtMovieYear).text = movies[position].year
        view.findViewById<TextView>(R.id.txtMovieGenre).text = movies[position].genre

        return view
    }
}