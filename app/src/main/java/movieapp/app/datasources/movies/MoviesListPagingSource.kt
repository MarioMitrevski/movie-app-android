package movieapp.app.datasources.movies

import androidx.paging.PagingState
import androidx.paging.rxjava3.RxPagingSource
import io.reactivex.rxjava3.core.Single
import movieapp.app.domain.movies.entities.MovieItem
import movieapp.app.domain.movies.entities.MoviesListCategory
import movieapp.app.domain.movies.entities.MoviesResponse
import movieapp.app.domain.movies.usecases.GetMoviesForCategory

class MoviesListPagingSource(
    private val getMoviesForCategory: GetMoviesForCategory,
    private val moviesListCategory: MoviesListCategory
) : RxPagingSource<Int, MovieItem>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, MovieItem>> {
        val nextPageNumber = params.key ?: 1
        return getMoviesForCategory(
            nextPageNumber,
            moviesListCategory
        )
            .map { toLoadResult(it, params, nextPageNumber) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(
        response: MoviesResponse,
        params: LoadParams<Int>,
        fetchedPageNumber: Int
    ): LoadResult<Int, MovieItem> {
        val nextKey = if (params.loadSize * (fetchedPageNumber + 1) < response.total_pages) {
            fetchedPageNumber + 1
        } else {
            null
        }
        return LoadResult.Page(
            data = response.results,
            prevKey = null,
            nextKey = nextKey
        )
    }

    override fun getRefreshKey(state: PagingState<Int, MovieItem>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}