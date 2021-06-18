package com.ariefrahman.core.utils

import com.ariefrahman.core.data.source.local.entity.MovieEntity
import com.ariefrahman.core.data.source.remote.response.MovieResponse
import com.ariefrahman.core.domain.model.Movie

object DataMapper {
    fun mapResponsesToEntities(input: List<MovieResponse>): List<MovieEntity> {
        val movieList = ArrayList<MovieEntity>()
        input.map {
            val movie = MovieEntity(
                it.id,
                it.overview,
                it.originalTitle,
                it.posterPath,
                it.releaseDate,
                it.voteAverage
            )
            movieList.add(movie)
        }
        return movieList
    }

    fun mapEntitiesToDomain(input: List<MovieEntity>): List<Movie> =
        input.map {
            Movie(
                it.id,
                it.overview,
                it.originalTitle,
                it.posterPath,
                it.releaseDate,
                it.voteAverage,
                it.favorite
            )
        }

    fun mapDomainToEntities(input: Movie): MovieEntity =
        MovieEntity(
            input.id,
            input.overview,
            input.originalTitle,
            input.posterPath,
            input.releaseDate,
            input.voteAverage,
            input.favorite
        )

}