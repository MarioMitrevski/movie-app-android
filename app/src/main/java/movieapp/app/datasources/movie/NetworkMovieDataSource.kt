package movieapp.app.datasources.movie

import io.reactivex.rxjava3.core.Single
import movieapp.app.data.movie.MovieDataSource
import movieapp.app.datasources.movie.network.MovieApi
import movieapp.app.domain.movies.entities.MovieDetails
import movieapp.app.domain.movies.entities.MoviesResponse
import movieapp.app.util.NetworkResponseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkMovieDataSource @Inject constructor(
    private val movieApi: MovieApi,
    private val networkResponseMapper: NetworkResponseMapper
) : MovieDataSource {

    override fun getPopularMovies(page: Int): Single<MoviesResponse> {
        return movieApi.getPopularMovies(page)
            .compose(networkResponseMapper.unWrapResponseWithError())
    }

    override fun getTopRatedMovies(page: Int): Single<MoviesResponse> {
        return movieApi.getTopRatedMovies(page)
            .compose(networkResponseMapper.unWrapResponseWithError())
    }

    override fun getMovieDetails(id: Int): Single<MovieDetails> {
        return movieApi.getMovieDetails(id)
            .compose(networkResponseMapper.unWrapResponseWithError())
    }
}