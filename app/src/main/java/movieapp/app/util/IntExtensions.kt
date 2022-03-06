package movieapp.app.util

fun Int.getFormattedMovieRuntime(): String {
    val hours = this / 60
    val minutes = this % 60

    return "${hours}h ${minutes}m"
}