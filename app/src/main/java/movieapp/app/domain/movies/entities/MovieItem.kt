package movieapp.app.domain.movies.entities

import java.time.LocalDate

data class MovieItem(
    val id: Int,
    val title: String,
    val poster_path: String,
    val overview: String,
    val release_date: LocalDate
)
