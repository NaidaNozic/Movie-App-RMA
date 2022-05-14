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
//valja
class ActorsFragment(name:String,id:Long?): Fragment() {
    private var movieName:String = name
    private var movieId:Long? = id
    private lateinit var movieRV:RecyclerView
    private var actorsList= listOf<String>()
    private lateinit var actorsRVSimpleAdapter:StringAdapter

    private var movieDetailViewModel =  MovieDetailViewModel(null,null, this@ActorsFragment::OpenActors)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.actors_fragment, container, false)

        movieRV = view.findViewById(R.id.actorsRecyclerView)
        actorsList = movieName?.let { movieDetailViewModel.getActorsByTitle(it) }!!
        movieRV.layoutManager = LinearLayoutManager(activity)
        actorsRVSimpleAdapter = StringAdapter(actorsList)
        movieRV.adapter = actorsRVSimpleAdapter
        movieId?.let { movieDetailViewModel.getActorsById(it) }
        return view;
    }
    fun OpenActors(actors:MutableList<String>){
        actorsList=actors
        actorsRVSimpleAdapter.lista=actors
        actorsRVSimpleAdapter.notifyDataSetChanged();
    }
}