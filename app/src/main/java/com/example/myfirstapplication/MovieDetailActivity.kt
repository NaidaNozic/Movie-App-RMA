package com.example.myfirstapplication

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.example.myfirstapplication.data.Movie
import com.example.myfirstapplication.viewmodel.MovieDetailViewModel

class MovieDetailActivity : AppCompatActivity() {
    private var movieDetailViewModel = MovieDetailViewModel()
    private lateinit var movie: Movie
    private lateinit var title : TextView
    private lateinit var overview : TextView
    private lateinit var releaseDate : TextView
    private lateinit var genre : TextView
    private lateinit var website : TextView
    private lateinit var poster : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        title = findViewById(R.id.movie_title)
        overview = findViewById(R.id.movie_overview)
        releaseDate = findViewById(R.id.movie_release_date)
        genre = findViewById(R.id.movie_genre)
        poster = findViewById(R.id.movie_poster)
        website = findViewById(R.id.movie_website)
        val extras = intent.extras
        if (extras != null) {//ovo extras su podaci koje smo poslali novom intentu preko putExtra()..a mi smo bili poslali movie_title
            movie = movieDetailViewModel.getMovieByTitle(extras.getString("movie_title",""))
            populateDetails()
        } else {
            finish()
        }

        website.setOnClickListener{ //Potrebno je napraviti da se pokrene web preglednik kada se klikne na link web stranice filma
            // i da se učita navedena stranica.
            showWebsite()
        }
    }
    private fun showWebsite(){
        val webIntent = Intent().apply {
            action = Intent.ACTION_VIEW
            setData(Uri.parse(movie.homepage))
        }
       /*
       ovo je stvaralo problem. Nije mi se tekst htio kopirati u searchText aplikacije
       try {
            startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            // Definisati naredbe ako ne postoji aplikacija za navedenu akciju
        }*/
        if (webIntent.resolveActivity(packageManager) != null) {
            startActivity(webIntent)
        }
    }
    private fun populateDetails() {
        title.text=movie.title
        releaseDate.text=movie.releaseDate
        genre.text=movie.genre
        website.text=movie.homepage
        overview.text=movie.overview
        val context: Context = poster.context
        var id: Int = context.resources
            .getIdentifier(movie.genre, "drawable", context.packageName)
        if (id===0) id=context.resources
            .getIdentifier("picture1", "drawable", context.packageName)
        poster.setImageResource(id)
    }

    }
