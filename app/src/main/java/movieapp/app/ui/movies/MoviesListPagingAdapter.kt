package movieapp.app.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import movieapp.app.databinding.VerticalMovieItemBinding
import movieapp.app.domain.movies.entities.MovieItem

class MoviesListPagingAdapter(
    diffCallback: DiffUtil.ItemCallback<MovieItem>,
    private val onMovieItemClickListener: (MovieItem) -> Unit,
) :
    PagingDataAdapter<MovieItem, MoviesListPagingAdapter.VerticalMovieItemViewHolder>(
        diffCallback
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VerticalMovieItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VerticalMovieItemBinding.inflate(layoutInflater, parent, false)
        return VerticalMovieItemViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: VerticalMovieItemViewHolder,
        position: Int
    ) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }

    inner class VerticalMovieItemViewHolder(private val binding: VerticalMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movieItem: MovieItem) {
            binding.apply {
                Glide.with(itemView.context).load(movieItem.poster_path)
                    .into(moviePosterImage)
                movieTitle.text = movieItem.title
                movieOverview.text = movieItem.overview
                movieReleaseDate.text = movieItem.release_date.year.toString()
            }
            itemView.setOnClickListener { onMovieItemClickListener(movieItem) }
        }
    }
}

object MovieItemDataComparator :
    DiffUtil.ItemCallback<MovieItem>() {
    override fun areItemsTheSame(
        oldItem: MovieItem,
        newItem: MovieItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: MovieItem,
        newItem: MovieItem
    ): Boolean {
        return oldItem == newItem
    }
}