package com.example.MoviesAPi.data.model

import com.google.gson.annotations.SerializedName

data class MovieData(
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String
)