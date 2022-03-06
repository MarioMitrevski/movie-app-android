package movieapp.app.domain.movies.entities

data class MovieItem(
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val release_date: String
)
