package movieapp.app.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import movieapp.app.domain.movies.entities.MovieItem
import movieapp.app.domain.movies.usecases.GetPopularMovies
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies
) : ViewModel() {

    private val _popularMoviesLiveData = MutableLiveData<List<MovieItem>>()
    val popularMoviesLiveData get() = _popularMoviesLiveData

    fun getPopularMovies() {
        getPopularMovies.invoke(1)
            .subscribe({
                _popularMoviesLiveData.value = it.results
            }, {

            })
    }
}

