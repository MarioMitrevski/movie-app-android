package movieapp.app.domain.movies.usecases

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import movieapp.app.data.movie.MovieRepository
import movieapp.app.domain.UseCase
import movieapp.app.domain.movies.entities.MoviesResponse
import javax.inject.Inject

@UseCase
class GetTopRatedMovies @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(page: Int): Single<MoviesResponse> {
        return movieRepository.getTopRatedMovies(page)
            .observeOn(AndroidSchedulers.mainThread())
    }
}
