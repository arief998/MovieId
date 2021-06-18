package com.ariefrahman.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie (
    var id: Int,
    var overview: String,
    var originalTitle: String,
    var posterPath: String,
    var releaseDate: String,
    var voteAverage: Double,
    var favorite: Boolean = false
): Parcelable