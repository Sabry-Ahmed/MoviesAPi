package com.example.MoviesAPi.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse<T>(
    @SerializedName("results")
    val results: List<T>
)
