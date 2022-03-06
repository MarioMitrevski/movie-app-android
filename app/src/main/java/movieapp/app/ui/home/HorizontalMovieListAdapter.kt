package movieapp.app.ui.home

import android.view.LayoutInflater
import android.view.MotionEvent.*
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import movieapp.app.R
import movieapp.app.databinding.HorizontalMovieItemBinding
import movieapp.app.databinding.HorizontalMovieListSeeAllItemBinding
import movieapp.app.domain.movies.entities.MovieItem

class HorizontalMovieListAdapter(
    private val movies: List<MovieItem>,
    private val onMovieItemClickListener: (MovieItem) -> Unit,
    private val onSeeAllClickListener: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.horizontal_movie_item -> {
                val binding = HorizontalMovieItemBinding.inflate(layoutInflater, parent, false)
                MovieItemViewHolder(binding)
            }
            R.layout.horizontal_movie_list_see_all_item -> {
                val binding =
                    HorizontalMovieListSeeAllItemBinding.inflate(layoutInflater, parent, false)
                SeeAllMoviesViewHolder(binding)
            }
            else -> {
                throw IllegalArgumentException("Illegal viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieItemViewHolder -> {
                holder.bind(movies[position])
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            movies.size -> R.layout.horizontal_movie_list_see_all_item
            else -> R.layout.horizontal_movie_item
        }
    }

    override fun getItemCount(): Int {
        return movies.size + 1
    }

    inner class MovieItemViewHolder(private val binding: HorizontalMovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnTouchListener { v, event ->
                when (event.action) {
                    ACTION_UP -> {
                        itemView.performClick()
                    }
                    ACTION_CANCEL -> {
                        itemView.animate().alpha(1f).scaleX(1f).scaleY(1f).start()
                    }
                    ACTION_DOWN -> {
                        itemView.animate().alpha(0.5f).scaleX(0.95f).scaleY(0.95f).start()
                    }
                }
                true
            }
        }

        fun bind(movieItem: MovieItem) {
            binding.apply {
                Glide.with(itemView.context).load(movieItem.poster_path)
                    .into(moviePosterImage)
                movieTitle.text = movieItem.title
            }
            itemView.setOnClickListener { onMovieItemClickListener(movieItem) }
        }
    }


    inner class SeeAllMoviesViewHolder(binding: HorizontalMovieListSeeAllItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnTouchListener { v, event ->
                when (event.action) {
                    ACTION_UP -> {
                        itemView.performClick()
                    }
                    ACTION_CANCEL -> {
                        itemView.animate().alpha(1f).scaleX(1f).scaleY(1f).start()
                    }
                    ACTION_DOWN -> {
                        itemView.animate().alpha(0.5f).scaleX(0.95f).scaleY(0.95f).start()
                    }
                }
                true
            }
            itemView.setOnClickListener { onSeeAllClickListener() }
        }
    }
}