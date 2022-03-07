package movieapp.app.ui.movies

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava3.flowable
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.core.Flowable
import movieapp.app.datasources.movies.MoviesListPagingSource
import movieapp.app.domain.movies.entities.MovieItem
import movieapp.app.domain.movies.entities.MoviesListCategory
import movieapp.app.domain.movies.usecases.GetMoviesForCategory
import movieapp.app.ui.BaseFragmentViewModel
import javax.inject.Inject

@HiltViewModel
class MoviesListViewModel @Inject constructor(
    private val getMoviesForCategory: GetMoviesForCategory
) : BaseFragmentViewModel() {

    lateinit var moviesForCategoryFlowable: Flowable<PagingData<MovieItem>>

    fun initMoviesForCategoryFlowable(moviesListCategory: MoviesListCategory) {
        moviesForCategoryFlowable = Pager(
            config = PagingConfig(
                pageSize = MOVIES_LIST_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = MOVIES_LIST_PAGE_SIZE
            ),
            initialKey = MOVIES_LIST_INITIAL_KEY,
            pagingSourceFactory = {
                MoviesListPagingSource(
                    getMoviesForCategory,
                    moviesListCategory
                )
            }
        ).flowable
    }


    companion object {
        private const val MOVIES_LIST_INITIAL_KEY = 1
        private const val MOVIES_LIST_PAGE_SIZE = 20
    }
}