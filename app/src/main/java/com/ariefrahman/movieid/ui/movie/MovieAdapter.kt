package com.ariefrahman.movieid.ui.movie

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ariefrahman.core.domain.model.Movie
import com.ariefrahman.movieid.databinding.MoviesItemApiBinding
import com.ariefrahman.movieid.ui.detail.DetailActivity
import com.ariefrahman.movieid.ui.detail.DetailActivity.Companion.EXTRA_MOVIE
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MovieAdapter: RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var mList = ArrayList<Movie>()
    companion object{
        const val URL = "https://image.tmdb.org/t/p/w500/"
    }

    fun setMovieList(movie: List<Movie>?){
        if(movie == null) return
        mList.clear()
        mList.addAll(movie)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieAdapter.MovieViewHolder {
        val binding = MoviesItemApiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieAdapter.MovieViewHolder, position: Int) {
        holder.bind(mList[position])
        
        holder.itemView.setOnClickListener { 
            val intent = Intent(it.context, DetailActivity::class.java)
            intent.putExtra(EXTRA_MOVIE, mList[holder.adapterPosition])
            it.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    inner class MovieViewHolder(private val binding: MoviesItemApiBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(movie: Movie){
            var format = SimpleDateFormat("yyyy-MM-dd", Locale.US)
            val newDate: Date = format.parse(movie.releaseDate)

            format = SimpleDateFormat("MMM dd, yyyy", Locale.US)
            val date = format.format(newDate)

            with(binding){
                tvTitle.text = movie.originalTitle
                tvGenre.text = movie.voteAverage.toString()
                tvDuration.text = date
                Glide.with(binding.root)
                    .load(URL+movie.posterPath)
                    .centerCrop()
                    .into(binding.imgPoster)
            }
        }
    }
}