package movieapp.app.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import movieapp.app.domain.movies.entities.MovieItem
import movieapp.app.domain.movies.usecases.GetTopRatedMovies
import movieapp.app.domain.movies.usecases.GetPopularMovies
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getTopRatedMovies: GetTopRatedMovies,
) : ViewModel() {

    private val _popularMoviesLiveData = MutableLiveData<List<MovieItem>>()
    val popularMoviesLiveData get() = _popularMoviesLiveData

    private val _topRatedMoviesLiveData = MutableLiveData<List<MovieItem>>()
    val topRatedMoviesLiveData get() = _topRatedMoviesLiveData

    fun getPopularMovies() {
        getPopularMovies.invoke(1)
            .subscribe({
                _popularMoviesLiveData.value = it.results
            }, {

            })
    }

    fun getTopRatedMovies() {
        getTopRatedMovies.invoke(1)
            .subscribe({
                _topRatedMoviesLiveData.value = it.results
            }, {

            })
    }
}

