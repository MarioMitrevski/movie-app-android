package movieapp.app.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import movieapp.app.databinding.FragmentMovieDetailsBinding
import movieapp.app.domain.movies.entities.MovieDetails
import movieapp.app.util.NetworkResult
import movieapp.app.util.getFormattedMovieRuntime
import movieapp.app.R
import com.google.android.flexbox.FlexWrap
import movieapp.app.util.MarginItemDecoration


@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {
    private var _binding: FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: MovieDetailsFragmentArgs by navArgs()
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieDetailsLiveDataObserver()
        initClickListeners()
        viewModel.getMovieDetails(args.id)
    }

    private fun setMovieDetailsLiveDataObserver() {
        viewModel.movieDetailsLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Loading -> {
                    binding.apply {
                        progressBar.isVisible = true
                        errorLayout.root.isVisible = false
                    }
                }
                is NetworkResult.Success -> {
                    binding.apply {
                        progressBar.isVisible = false
                        errorLayout.root.isVisible = false
                    }
                    setMovieDetailsData(it.data)
                }
                is NetworkResult.Error -> {
                    binding.apply {
                        progressBar.isVisible = false
                        errorLayout.root.isVisible = true
                        errorLayout.errorMessage.text = it.message
                        Glide.with(requireContext()).load(R.drawable.error)
                            .into(errorLayout.errorIcon)
                    }
                }
            }
        }
    }

    private fun setMovieDetailsData(movieDetails: MovieDetails) {
        binding.apply {
            Glide.with(requireContext()).load(movieDetails.poster_path).optionalCenterCrop()
                .into(moviePosterImage)
            movieTitle.text = movieDetails.title
            movieReleaseDate.text = movieDetails.release_date.year.toString()
            movieRuntime.text = movieDetails.runtime.getFormattedMovieRuntime()
            movieOverview.text = movieDetails.overview
            movieGenres.apply {
                val flexboxLayoutManager = FlexboxLayoutManager(requireContext())
                flexboxLayoutManager.flexDirection = FlexDirection.ROW
                flexboxLayoutManager.flexWrap = FlexWrap.WRAP
                layoutManager = flexboxLayoutManager
                adapter = MovieGenresAdapter(movieDetails.genres.map { it.name })
                addItemDecoration(
                    MarginItemDecoration(
                        resources.getDimensionPixelSize(
                            R.dimen.genre_item_margin
                        ),
                        resources.getDimensionPixelSize(
                            R.dimen.genre_item_margin
                        )
                    )
                )
            }
        }
    }

    private fun initClickListeners() {
        binding.apply {
            errorLayout.retryButton.setOnClickListener { viewModel.getMovieDetails(args.id) }
            backButton.setOnClickListener { findNavController().popBackStack() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}