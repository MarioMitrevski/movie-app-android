package movieapp.app.datasources.movie

import io.reactivex.rxjava3.core.Single
import movieapp.app.data.movie.MovieDataSource
import movieapp.app.datasources.movie.network.MovieApi
import movieapp.app.domain.movies.entities.MoviesResponse
import movieapp.app.util.unWrapResponseWithError
import javax.inject.Inject

class NetworkMovieDataSource @Inject constructor(
    private val movieApi: MovieApi
) : MovieDataSource {

    override fun getPopularMovies(page: Int): Single<MoviesResponse> {
        return movieApi.getPopularMovies(page).compose(unWrapResponseWithError())
    }

}