package movieapp.app.domain.movies.usecases

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import movieapp.app.BuildConfig
import movieapp.app.data.movie.MovieRepository
import movieapp.app.domain.UseCase
import movieapp.app.domain.genres.entities.Genre
import movieapp.app.domain.movies.entities.MovieDetails
import javax.inject.Inject

@UseCase
class GetMovieDetails @Inject constructor(
    private val movieRepository: MovieRepository
) {

    operator fun invoke(id: Int): Single<MovieDetails> {
        return movieRepository.getMovieDetails(id)
            .map { it.copy(poster_path = BuildConfig.IMAGES_URL + it.poster_path) }
            .observeOn(AndroidSchedulers.mainThread())
    }

}