package movieapp.app.datasources.movie.network

import io.reactivex.rxjava3.core.Single
import movieapp.app.domain.movies.entities.MovieDetails
import movieapp.app.domain.movies.entities.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<Response<MoviesResponse>>

    @GET("movie/top_rated")
    fun getTopRatedMovies(@Query("page") page: Int): Single<Response<MoviesResponse>>

    @GET("movie/{movie_id}")
    fun getMovieDetails(@Path("movie_id") movieId: Int): Single<Response<MovieDetails>>
}