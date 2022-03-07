package movieapp.app.ui.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import movieapp.app.databinding.LoadingItemBinding

class LoadStateViewHolder(private val binding: LoadingItemBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bind(loadState: LoadState) {
        binding.progressBar.isVisible = loadState is LoadState.Loading
    }

    companion object {
        fun create(parent: ViewGroup): LoadStateViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = LoadingItemBinding.inflate(layoutInflater, parent, false)
            return LoadStateViewHolder(binding)
        }
    }
}