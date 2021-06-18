package com.ariefrahman.core.data

import com.ariefrahman.core.data.source.local.LocalDataSource
import com.ariefrahman.core.data.source.remote.ApiResponse
import com.ariefrahman.core.data.source.remote.RemoteDataSource
import com.ariefrahman.core.data.source.remote.response.MovieResponse
import com.ariefrahman.core.domain.model.Movie
import com.ariefrahman.core.domain.repository.IMovieRepository
import com.ariefrahman.core.utils.AppExecutors
import com.ariefrahman.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors
): IMovieRepository {
    override fun getMovies(): Flow<Resource<List<Movie>>> {
        return object : NetworkBoundSource<List<Movie>, List<MovieResponse>>() {
            override fun loadFromDB(): Flow<List<Movie>> {
                return localDataSource.getAllMovie().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Movie>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getAllMovie()
            }

            override suspend fun saveCallResult(data: List<MovieResponse>) {
                val movieList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insert(movieList)
            }
        }.asFlow()
    }

    override fun setMovieFavorite(movie: Movie, state: Boolean) {
        val movieEntity = DataMapper.mapDomainToEntities(movie)
        appExecutors.diskIO().execute { localDataSource.setFavorite(movieEntity, state) }
    }

    override fun getListFavorite(): Flow<List<Movie>> =
        localDataSource.getListMovieFavorite().map { DataMapper.mapEntitiesToDomain(it) }

    override fun getMovieById(movieId: Int): Flow<List<Movie>> =
        localDataSource.getMovieById(movieId).map {
            DataMapper.mapEntitiesToDomain(it)
        }
}