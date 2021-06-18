package com.ariefrahman.core.domain.repository

import com.ariefrahman.core.data.Resource
import com.ariefrahman.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface IMovieRepository {
    fun getMovies(): Flow<Resource<List<Movie>>>

    fun setMovieFavorite(movie: Movie, state: Boolean)

    fun getListFavorite(): Flow<List<Movie>>

    fun getMovieById(movieId: Int): Flow<List<Movie>>
}