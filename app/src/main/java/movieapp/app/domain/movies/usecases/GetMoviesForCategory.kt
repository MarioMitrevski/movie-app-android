package movieapp.app.domain.movies.usecases

import io.reactivex.rxjava3.core.Single
import movieapp.app.domain.UseCase
import movieapp.app.domain.movies.entities.MoviesListCategory
import movieapp.app.domain.movies.entities.MoviesResponse
import javax.inject.Inject

@UseCase
class GetMoviesForCategory @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getTopRatedMovies: GetTopRatedMovies
) {

    operator fun invoke(page: Int, moviesListCategory: MoviesListCategory): Single<MoviesResponse> {
        return when (moviesListCategory) {
            MoviesListCategory.TOP_RATED -> {
                getTopRatedMovies(page)
            }
            MoviesListCategory.POPULAR -> {
                getPopularMovies(page)
            }
        }
    }
}