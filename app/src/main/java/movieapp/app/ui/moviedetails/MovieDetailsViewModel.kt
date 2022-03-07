package movieapp.app.ui.moviedetails

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import movieapp.app.R
import movieapp.app.domain.movies.entities.MovieDetails
import movieapp.app.domain.movies.usecases.GetMovieDetails
import movieapp.app.ui.BaseFragmentViewModel
import movieapp.app.util.NetworkConnectivity
import movieapp.app.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetails: GetMovieDetails,
    private val networkConnectivity: NetworkConnectivity,
    private val resources: Resources
) : BaseFragmentViewModel() {

    private val _movieDetailsLiveData = MutableLiveData<NetworkResult<MovieDetails>>()
    val movieDetailsLiveData get() = _movieDetailsLiveData

    fun getMovieDetails(id: Int) {
        if (!networkConnectivity.isNetworkAvailable) {
            _movieDetailsLiveData.value =
                NetworkResult.Error(resources.getString(R.string.no_internet_connection))
            return
        }
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