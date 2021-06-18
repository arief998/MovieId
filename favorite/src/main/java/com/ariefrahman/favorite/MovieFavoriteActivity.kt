package com.ariefrahman.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.ariefrahman.favorite.databinding.ActivityMovieFavoriteBinding
import com.ariefrahman.movieid.di.viewModelModule
import com.ariefrahman.movieid.ui.movie.MovieAdapter
import com.ariefrahman.movieid.ui.movie.MovieViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import java.lang.Exception

class MovieFavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMovieFavoriteBinding
    private val viewModel: MovieViewModel by viewModel()
    private lateinit var mAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite"

        mAdapter = MovieAdapter()
        loadKoinModules(viewModelModule)

        viewModel.getListMovieFavorite().observe(this, { listMovie ->
            try {
                mAdapter.setMovieList(listMovie)

                with(binding.rvMovies){
                    layoutManager = LinearLayoutManager(context)
                    setHasFixedSize(true)
                    adapter = mAdapter

                }
            } catch (e: Exception){
                e.printStackTrace()
            }

        })
    }
}