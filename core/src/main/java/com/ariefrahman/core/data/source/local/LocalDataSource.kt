package com.ariefrahman.core.data.source.local

import com.ariefrahman.core.data.source.local.entity.MovieEntity
import com.ariefrahman.core.data.source.local.room.MovieDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(
    private val movieDao: MovieDao
) {
    suspend fun insert(movieEntity: List<MovieEntity>) {
        movieDao.insert(movieEntity)
    }

    fun getAllMovie(): Flow<List<MovieEntity>> = movieDao.getAllMovie()

    fun setFavorite(movie: MovieEntity, newState: Boolean){
        movie.favorite = newState
        movieDao.updateMovie(movie)
    }

    fun getListMovieFavorite(): Flow<List<MovieEntity>> = movieDao.getListFavoriteMovie()

    fun getMovieById(movieId: Int): Flow<List<MovieEntity>> = movieDao.getMovieById(movieId)
}