package movieapp.app.domain.movies.entities

data class MoviesResponse(
    val page: Int,
    var results: MutableList<MovieItem>,
    val total_pages: Int
)