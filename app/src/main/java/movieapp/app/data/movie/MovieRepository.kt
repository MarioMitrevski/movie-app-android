package movieapp.app.data.movie

import io.reactivex.rxjava3.core.Single
import movieapp.app.domain.movies.entities.MovieDetails
import movieapp.app.domain.movies.entities.MoviesResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    private val movieDataSource: MovieDataSource
) {

    fun getPopularMovies(page: Int): Single<MoviesResponse> {
        return movieDataSource.getPopularMovies(page)
    }

    fun getTopRatedMovies(page: Int): Single<MoviesResponse> {
        return movieDataSource.getTopRatedMovies(page)
    }

    fun getMovieDetails(id: Int): Single<MovieDetails> {
        return movieDataSource.getMovieDetails(id)
    }

}