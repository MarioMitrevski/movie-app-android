package movieapp.app.ui.moviedetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import movieapp.app.databinding.MovieGenreListItemBinding

class MovieGenresAdapter(
    private val genres: List<String>
) : RecyclerView.Adapter<MovieGenresAdapter.GenreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenreViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = MovieGenreListItemBinding.inflate(layoutInflater, parent, false)
        return GenreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GenreViewHolder, position: Int) {
        holder.bind(genres[position])
    }

    override fun getItemCount(): Int {
        return genres.size
    }

    inner class GenreViewHolder(private val binding: MovieGenreListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(genre: String) {
            binding.apply {
                genreName.text = genre
            }
        }
    }
}