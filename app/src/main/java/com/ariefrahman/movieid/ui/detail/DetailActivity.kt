package com.ariefrahman.movieid.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.ariefrahman.core.domain.model.Movie
import com.ariefrahman.movieid.R
import com.ariefrahman.movieid.databinding.ActivityDetailBinding
import com.ariefrahman.movieid.ui.movie.MovieAdapter.Companion.URL
import com.ariefrahman.movieid.ui.movie.MovieViewModel
import com.bumptech.glide.Glide
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    companion object{
        const val EXTRA_MOVIE = "extra_movie"
    }

    private lateinit var binding: ActivityDetailBinding
    private val movieViewModel: MovieViewModel by viewModel()
    private lateinit var movieFavorite: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent.extras

        if(intent != null){
            movieFavorite = intent.getParcelable<Movie>(EXTRA_MOVIE) as Movie

            movieViewModel.getMovieById(movieFavorite.id).observe(this, { movie ->
                initViewMovie(movie[0])
                checkState(movie[0].favorite)

                binding.btnFavoriteMovie.setOnClickListener {
                    if(movie[0].favorite){
                        removeFromFavorite(movie[0])
                    } else {
                        addToFavorite(movie[0])
                    }
                }
            })
        }
        supportActionBar?.title = movieFavorite.originalTitle
    }

    private fun initViewMovie(movie: Movie){
        with(binding){
            var format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val newDate = format.parse(movie.releaseDate)

            format = SimpleDateFormat("MMM dd, yyyy", Locale.US)
            val date = format.format(newDate)

            tvTitleDetail.text = movie.originalTitle
            tvSynopsisDetail.text = movie.overview
            tvRate.text = movie.voteAverage.toString()
            tvDate.text = date
            Glide.with(binding.root)
                .load(URL + movie.posterPath)
                .override(300, 400)
                .into(imgPosterDetail)
            btnFavoriteMovie.visibility = View.VISIBLE
        }
    }

    private fun addToFavorite(movie: Movie) {
        movie.favorite = true
        movieViewModel.addToFavorite(movie)
    }

    private fun removeFromFavorite(movie: Movie){
        movie.favorite = false
        movieViewModel.addToFavorite(movie)
    }

    private fun checkState(state: Boolean) {
        return if(state){
            if(binding.btnFavoriteMovie.isVisible){
                binding.btnFavoriteMovie.background = ContextCompat.getDrawable(this, R.drawable.ic_favorite_red)
            }else {
                binding.btnFavoriteShow.background = ContextCompat.getDrawable(this, R.drawable.ic_favorite_red)
            }
        }else {
            if(binding.btnFavoriteMovie.isVisible){
                binding.btnFavoriteMovie.background = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
            } else {
                binding.btnFavoriteShow.background = ContextCompat.getDrawable(this, R.drawable.ic_favorite_white)
            }

        }
    }
}