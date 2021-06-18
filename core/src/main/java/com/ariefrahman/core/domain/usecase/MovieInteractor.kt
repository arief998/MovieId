package com.ariefrahman.core.domain.usecase

import com.ariefrahman.core.data.Resource
import com.ariefrahman.core.domain.model.Movie
import com.ariefrahman.core.domain.repository.IMovieRepository
import kotlinx.coroutines.flow.Flow

class MovieInteractor(private val movieRepository: IMovieRepository): MovieUsecase {
    override fun getMovies(): Flow<Resource<List<Movie>>> =
        movieRepository.getMovies()

    override fun setMovieFavorite(movie: Movie, state: Boolean) =
        movieRepository.setMovieFavorite(movie, state)

    override fun getListFavorite(): Flow<List<Movie>> =
        movieRepository.getListFavorite()

    override fun getMovieById(movieId: Int): Flow<List<Movie>> =
        movieRepository.getMovieById(movieId)
}