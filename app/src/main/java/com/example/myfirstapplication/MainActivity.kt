package com.example.myfirstapplication

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.data.Movie
import com.example.myfirstapplication.view.MovieListAdapter
import com.example.myfirstapplication.viewmodel.MovieListViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var favoriteMovies: RecyclerView
    private lateinit var favoriteMoviesAdapter: MovieListAdapter
    private lateinit var recentMovies: RecyclerView
    private lateinit var recentMoviesAdapter: MovieListAdapter
    private var movieListViewModel = MovieListViewModel()
    private lateinit var searchText: EditText

    private val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
    private val br: BroadcastReceiver = MyBroadcastReceiver()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        favoriteMovies = findViewById(R.id.favouriteMovies)
        recentMovies = findViewById(R.id.upcomingMovies)
        searchText = findViewById(R.id.searchText) //dodano
        favoriteMovies.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        recentMovies.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        favoriteMoviesAdapter = MovieListAdapter(arrayListOf()){ movie -> showMovieDetails(movie) }//dodala akciju koja se desava onda kada
                                                                                                  //se desi klik na film
        recentMoviesAdapter= MovieListAdapter(arrayListOf()){ movie -> showMovieDetails(movie) } //izmijenjeno
        favoriteMovies.adapter = favoriteMoviesAdapter
        recentMovies.adapter = recentMoviesAdapter
        favoriteMoviesAdapter.updateMovies(movieListViewModel.getFavoriteMovies())
        recentMoviesAdapter.updateMovies(movieListViewModel.getRecentMovies())

        if(intent?.action == Intent.ACTION_SEND && intent?.type == "text/plain")
            handleSendText(intent)
    }
    //aplikacija treba da odgovara na akciju tipa ACTION_SEND ,tj. ako se putem te akcije
    //prosljeđuje tekst. Navedeni tekst treba da popuni editText u početnoj aktivnosti koji se nalazi iznad button-a
    //pretrage.Sljedeci dio koda ispod to radi
    private fun handleSendText(intent: Intent) {
        intent.getStringExtra(Intent.EXTRA_TEXT)?.let {
            searchText.setText(it)
        }
    }
    private fun showMovieDetails(movie: Movie) {
        val intent = Intent(this, MovieDetailActivity::class.java).apply {
            putExtra("movie_title", movie.title)
        }
        startActivity(intent)
    }
    override fun onResume() {
        super.onResume()
        registerReceiver(br, intentFilter)
    }

    override fun onPause() {
        unregisterReceiver(br)
        super.onPause()
    }

}
