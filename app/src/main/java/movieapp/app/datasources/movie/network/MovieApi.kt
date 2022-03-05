package movieapp.app.datasources.movie.network

import io.reactivex.rxjava3.core.Single
import movieapp.app.domain.movies.entities.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Single<Response<MoviesResponse>>
}