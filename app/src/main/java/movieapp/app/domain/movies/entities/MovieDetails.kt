package movieapp.app.domain.movies.entities

import movieapp.app.domain.genres.entities.Genre
import java.time.LocalDate

data class MovieDetails(
    val id: Int,
    val title:String,
    val runtime: Int,
    val release_date: LocalDate,
    val poster_path: String,
    val overview: String,
    val genres: List<Genre>
)