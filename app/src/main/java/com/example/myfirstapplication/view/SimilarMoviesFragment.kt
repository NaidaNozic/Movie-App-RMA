package com.example.myfirstapplication.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.R
import com.example.myfirstapplication.viewmodel.MovieDetailViewModel

class SimilarMoviesFragment(name:String): Fragment() {
    private lateinit var similarMovies: RecyclerView
    private var movieDetailViewModel = MovieDetailViewModel()
    private var movieTitle: String=name

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.similar_movies_fragment, container, false)
        similarMovies = view.findViewById(R.id.similarRecyclerView)
        similarMovies.layoutManager =  LinearLayoutManager(activity)
        similarMovies.adapter=StringAdapter(movieDetailViewModel.getSimilarByMovie(movieTitle))
        return view;
    }
}