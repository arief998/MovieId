package com.ariefrahman.core.data.source.remote

import com.ariefrahman.core.BuildConfig
import com.ariefrahman.core.data.source.remote.response.ListMovieResponse
import retrofit2.http.GET

interface ApiService {
    @GET("movie/popular?api_key=809e0a1f6d47ca83b2ae328adb012a0b")
    suspend fun getMovie(): ListMovieResponse
}