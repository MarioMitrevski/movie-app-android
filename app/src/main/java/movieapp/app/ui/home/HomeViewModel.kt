package movieapp.app.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import movieapp.app.domain.movies.entities.MovieItem
import movieapp.app.domain.movies.usecases.GetTopRatedMovies
import movieapp.app.domain.movies.usecases.GetPopularMovies
import movieapp.app.exceptions.ApiException
import movieapp.app.util.NetworkResult
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
) : ViewModel() {

    private val _popularMoviesLiveData = MutableLiveData<NetworkResult<List<MovieItem>>>()
    val popularMoviesLiveData get() = _popularMoviesLiveData

    private val _topRatedMoviesLiveData = MutableLiveData<NetworkResult<List<MovieItem>>>()
    val topRatedMoviesLiveData get() = _topRatedMoviesLiveData

    fun getPopularMovies() {
        getPopularMovies.invoke(1)
            .doOnSubscribe { _popularMoviesLiveData.value = NetworkResult.Loading() }
            .subscribe({
                _popularMoviesLiveData.value = NetworkResult.Success(it.results)
            }, {
                _popularMoviesLiveData.value = NetworkResult.Error(it.message)
            })
    }

    fun getTopRatedMovies() {
        getTopRatedMovies.invoke(1)
            .doOnSubscribe { _topRatedMoviesLiveData.value = NetworkResult.Loading() }
            .subscribe({
                _topRatedMoviesLiveData.value = NetworkResult.Success(it.results)
            }, {
                _topRatedMoviesLiveData.value = NetworkResult.Error(it.message)
            })
    }
}