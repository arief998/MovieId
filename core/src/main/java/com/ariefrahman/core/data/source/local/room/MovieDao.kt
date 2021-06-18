package com.ariefrahman.core.data.source.local.room

import androidx.room.*
import com.ariefrahman.core.data.source.local.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(movie: List<MovieEntity>)

    @Delete
    fun delete(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity")
    fun getAllMovie(): Flow<List<MovieEntity>>

    @Update
    fun updateMovie(movie: MovieEntity)

    @Query("SELECT * FROM MovieEntity WHERE favorite = 1")
    fun getListFavoriteMovie(): Flow<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE id = :movieId")
    fun getMovieById(movieId: Int): Flow<List<MovieEntity>>

}