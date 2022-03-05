package movieapp.app.domain.movies.entities

data class MoviesResponse(
    val page: Int,
    val results: List<MovieItem>,
    val total_pages: Int
)