package movieapp.app.ui.movies

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import movieapp.app.domain.movies.entities.MovieDetails
import movieapp.app.domain.movies.usecases.GetMovieDetails
import movieapp.app.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails
) : ViewModel() {

    private val _movieDetailsLiveData = MutableLiveData<NetworkResult<MovieDetails>>()
    val movieDetailsLiveData get() = _movieDetailsLiveData

    fun getMovieDetails(id: Int) {
        getMovieDetails.invoke(id)
            .doOnSubscribe { _movieDetailsLiveData.value = NetworkResult.Loading() }
            .subscribe({
                _movieDetailsLiveData.value = NetworkResult.Success(it)
            }, {
                _movieDetailsLiveData.value = NetworkResult.Error(it.message)
            })
    }
}