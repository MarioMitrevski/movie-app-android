package movieapp.app.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import movieapp.app.BuildConfig
import movieapp.app.databinding.HorizontalMovieItemBinding
import movieapp.app.domain.movies.entities.MovieItem

class HorizontalMovieListAdapter(
    private val movies: List<MovieItem>,
    private val onMovieItemClickListener: (MovieItem) -> Unit
) : RecyclerView.Adapter<HorizontalMovieListAdapter.MovieItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = HorizontalMovieItemBinding.inflate(layoutInflater, parent, false)
        return MovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieItemViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class MovieItemViewHolder(private val binding: HorizontalMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieItem: MovieItem) {
            binding.apply {
                Glide.with(itemView.context).load(BuildConfig.IMAGES_URL + movieItem.poster_path)
                    .into(moviePosterImage)
                movieTitle.text = movieItem.title
            }
            itemView.setOnClickListener { onMovieItemClickListener(movieItem) }
        }
    }


}