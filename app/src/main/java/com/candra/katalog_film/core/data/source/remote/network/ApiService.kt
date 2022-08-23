package com.candra.katalog_film.core.data.source.remote.network


import com.candra.katalog_film.core.data.source.remote.response.detailresponse.DetailResponse
import com.candra.katalog_film.core.data.source.remote.response.movie_recomendation_response.MovieRecomendationResponse
import com.candra.katalog_film.core.data.source.remote.response.movieresponse.MovieResponse
import com.candra.katalog_film.core.data.source.remote.response.peopleresponse.PeopleResponse
import com.candra.katalog_film.core.data.source.remote.response.peopleresponse.detailpeopleresponse.DetailPeopleResponse
import com.candra.katalog_film.core.data.source.remote.response.trailerresponse.TrailerResponse
import com.candra.katalog_film.core.data.source.remote.response.tv_recomendation_response.TvRecomendationResponse
import com.candra.katalog_film.core.data.source.remote.response.tv_show_response.TvShowResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    // Untuk playing skrng
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    // Untuk yang airing_today tv
    @GET("tv/airing_today")
    suspend fun getTvShowPlayingNow(
        @Query("api_key") apiKey: String,
        @Query("language") languages: String = "en-US",
        @Query("page") page: Int
    ): Response<TvShowResponse>

    // Untuk trending movie
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingMovieAndTvShow(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String
    ): Response<MovieResponse>

    //  trending week
    @GET("trending/{media_type}/{time_window}")
    suspend fun getTrendingTvShow(
        @Path("media_type") mediaType: String,
        @Path("time_window") timeWindow: String,
        @Query("api_key") apiKey: String
    ): Response<TvShowResponse>

    // Untuk Movie Populer
    @GET("movie/popular")
    suspend fun getPopularMovie(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<MovieResponse>

    // Untuk TV Populer
    @GET("tv/popular")
    suspend fun getPopularTvShow(
        @Query("api_key") apiKey: String,
        @Query("page") page: Int
    ): Response<TvShowResponse>

    // Untuk Search
    @GET("search/movie")
    suspend fun searchMovie(
        @Query("api_key") apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") include_adult: Boolean = false
    ): Response<MovieRecomendationResponse>

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<DetailResponse>

    @GET("tv/{tv_id}")
    suspend fun getDetailTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<DetailResponse>

    @GET("tv/{tv_id}/credits")
    suspend fun getCreditsTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<PeopleResponse>

    @GET("movie/{movie_id}/credits")
    suspend fun getCreditsMovie(
        @Path("movie_id") moveId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<PeopleResponse>

    @GET("movie//{movie_id}/videos")
    suspend fun getVideoMovie(
        @Path("movie_id") moveId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<TrailerResponse>

    @GET("tv/{tv_id}/videos")
    suspend fun getTvShowVideo(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<TrailerResponse>

    @GET("tv/{tv_id}/recommendations")
    suspend fun getRecommendationTvShow(
        @Path("tv_id") tvId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<TvRecomendationResponse>

    @GET("movie/{movie_id}/recommendations")
    suspend fun getRecommendationMovie(
        @Path("movie_id") moveId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<MovieRecomendationResponse>

    @GET("person/{person_id}")
    suspend fun getPeopleById(
        @Path("person_id") personId: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US"
    ): Response<DetailPeopleResponse>

    @GET("movie/top_rated")
    suspend fun getTopRated(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<MovieResponse>

    @GET("tv/top_rated")
    suspend fun getTopRatedTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String = "en-US",
        @Query("page") page: Int
    ): Response<TvShowResponse>

    @GET("search/tv")
    suspend fun searchTvShow(
        @Query("api_key")apiKey: String,
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("include_adult") include_adult: Boolean = false
    ): Response<TvRecomendationResponse>
}