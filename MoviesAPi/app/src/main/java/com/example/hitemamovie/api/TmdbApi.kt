package com.example.MoviesAPi.api

import com.example.MoviesAPi.data.model.ApiResponse
import com.example.MoviesAPi.data.model.MovieData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface TmdbApi {
    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYWMwMWQwNThlYzljMDBhYmFiYTQwN2E0MDVhZGE5MyIsInN1YiI6IjYzZmY1MTIyOTY1M2Y2MDBiNThiOTE3OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.yz_tMi39k4L-td563CunwReO-ViMWa9YqEuleW7sQFo")
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "fr-FR",
        @Query("page") page: Int = 1
    ): Response<ApiResponse<MovieData>>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYWMwMWQwNThlYzljMDBhYmFiYTQwN2E0MDVhZGE5MyIsInN1YiI6IjYzZmY1MTIyOTY1M2Y2MDBiNThiOTE3OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.yz_tMi39k4L-td563CunwReO-ViMWa9YqEuleW7sQFo")
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "fr-FR",
        @Query("page") page: Int = 1
    ): Response<ApiResponse<MovieData>>

    @Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIxYWMwMWQwNThlYzljMDBhYmFiYTQwN2E0MDVhZGE5MyIsInN1YiI6IjYzZmY1MTIyOTY1M2Y2MDBiNThiOTE3OCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.yz_tMi39k4L-td563CunwReO-ViMWa9YqEuleW7sQFo")
    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(
        @Query("language") language: String = "fr-FR",
        @Query("page") page: Int = 1
    ): Response<ApiResponse<MovieData>>
}

fun createTmdbApi(): TmdbApi {
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(TmdbApi::class.java)
}