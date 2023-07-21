package com.example.MoviesAPi.data.repository

import com.example.MoviesAPi.api.TmdbApi
import com.example.MoviesAPi.data.model.MovieData

class MovieRepository(private val tmdbApi: TmdbApi) {
    suspend fun getPopularMovies(): List<MovieData> {
        val response = tmdbApi.getPopularMovies()
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getTopRatedMovies(): List<MovieData> {
        val response = tmdbApi.getTopRatedMovies()
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }

    suspend fun getUpcomingMovies(): List<MovieData> {
        val response = tmdbApi.getUpcomingMovies()
        return if (response.isSuccessful) {
            response.body()?.results ?: emptyList()
        } else {
            emptyList()
        }
    }
}