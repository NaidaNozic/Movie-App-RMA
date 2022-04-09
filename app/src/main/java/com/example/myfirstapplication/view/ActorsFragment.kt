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

class ActorsFragment(name: String): Fragment() {
    private lateinit var actors: RecyclerView
    private var movieDetailViewModel = MovieDetailViewModel()
    private var movieTitle: String=name

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.actors_fragment, container, false)
        actors = view.findViewById(R.id.actorsRecyclerView)
        actors.layoutManager =  LinearLayoutManager(activity)
        actors.adapter=StringAdapter(movieDetailViewModel.getActorsByMovie(movieTitle))
        return view;
    }
}