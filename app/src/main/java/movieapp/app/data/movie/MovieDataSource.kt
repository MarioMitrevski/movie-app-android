package movieapp.app.data.movie

import io.reactivex.rxjava3.core.Single
import movieapp.app.domain.movies.entities.MovieDetails
import movieapp.app.domain.movies.entities.MoviesResponse

interface MovieDataSource {
    fun getPopularMovies(page: Int): Single<MoviesResponse>
    fun getTopRatedMovies(page: Int): Single<MoviesResponse>
    fun getMovieDetails(id: Int): Single<MovieDetails>
}