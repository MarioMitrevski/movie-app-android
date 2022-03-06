package movieapp.app.ui.moviedetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import movieapp.app.domain.movies.entities.MovieDetails
import movieapp.app.domain.movies.usecases.GetMovieDetails
import movieapp.app.ui.BaseFragmentViewModel
import movieapp.app.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails
) : BaseFragmentViewModel() {

    private val _movieDetailsLiveData = MutableLiveData<NetworkResult<MovieDetails>>()
    val movieDetailsLiveData get() = _movieDetailsLiveData

    fun getMovieDetails(id: Int) {
        compositeDisposable.add(getMovieDetails.invoke(id)
            .doOnSubscribe { _movieDetailsLiveData.value = NetworkResult.Loading() }
            .subscribe({
                _movieDetailsLiveData.value = NetworkResult.Success(it)
            }, {
                _movieDetailsLiveData.value = NetworkResult.Error(it.message)
            })
        )
    }
}