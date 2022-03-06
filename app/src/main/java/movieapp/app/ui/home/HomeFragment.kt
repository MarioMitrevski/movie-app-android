package movieapp.app.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import movieapp.app.R
import movieapp.app.databinding.FragmentHomeBinding
import movieapp.app.domain.movies.entities.MoviesListCategory
import movieapp.app.util.MarginItemDecoration
import movieapp.app.util.NetworkResult
import java.time.LocalTime

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setWelcomeMessage()
        initViews()
        initClickListeners()
        setPopularMoviesLiveDataObserver()
        setTopRatedMoviesLiveDataObserver()

        viewModel.getPopularMovies()
        viewModel.getTopRatedMovies()
    }

    private fun setPopularMoviesLiveDataObserver() {
        viewModel.apply {
            popularMoviesLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResult.Loading -> {
                        binding.popularMovies.apply {
                            progressBar.isVisible = true
                            movieListError.root.isVisible = false
                        }
                    }
                    is NetworkResult.Success -> {
                        binding.popularMovies.apply {
                            progressBar.isVisible = false
                            movieListError.root.isVisible = false
                            movieListRecyclerView.apply {
                                adapter = HorizontalMovieListAdapter(
                                    it.data,
                                    onMovieItemClickListener = { movieItem ->
                                        navigateToMovieDetails(movieItem.id)
                                    },
                                    onSeeAllClickListener = {
                                        navigateToSeeAllMoviesFromCategory(MoviesListCategory.POPULAR)
                                    })
                                addItemDecoration(
                                    MarginItemDecoration(
                                        horizontalSpaceSize = resources.getDimensionPixelSize(
                                            R.dimen.horizontal_movie_item_horizontal_margin
                                        )
                                    )
                                )
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        binding.popularMovies.apply {
                            progressBar.isVisible = false
                            movieListError.root.isVisible = true
                            movieListError.errorMessage.text = it.message
                            Glide.with(requireContext()).load(R.drawable.error)
                                .into(movieListError.errorIcon)
                        }
                    }
                }
            }
        }
    }

    private fun setTopRatedMoviesLiveDataObserver() {
        viewModel.apply {
            topRatedMoviesLiveData.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkResult.Loading -> {
                        binding.topRatedMovies.apply {
                            progressBar.isVisible = true
                            movieListError.root.isVisible = false
                        }
                    }
                    is NetworkResult.Success -> {
                        binding.topRatedMovies.apply {
                            progressBar.isVisible = false
                            movieListError.root.isVisible = false
                            movieListRecyclerView.apply {
                                adapter = HorizontalMovieListAdapter(it.data,
                                    onMovieItemClickListener = { movieItem ->
                                        navigateToMovieDetails(movieItem.id)
                                    },
                                    onSeeAllClickListener = {
                                        navigateToSeeAllMoviesFromCategory(MoviesListCategory.TOP_RATED)
                                    })
                                addItemDecoration(
                                    MarginItemDecoration(
                                        horizontalSpaceSize = resources.getDimensionPixelSize(
                                            R.dimen.horizontal_movie_item_horizontal_margin
                                        )
                                    )
                                )
                            }
                        }
                    }
                    is NetworkResult.Error -> {
                        binding.topRatedMovies.apply {
                            progressBar.isVisible = false
                            movieListError.root.isVisible = true
                            movieListError.errorMessage.text = it.message
                            Glide.with(requireContext()).load(R.drawable.error)
                                .into(movieListError.errorIcon)
                        }
                    }
                }
            }
        }
    }

    private fun navigateToSeeAllMoviesFromCategory(moviesListCategory: MoviesListCategory) {
        findNavController().navigate(HomeFragmentDirections.toMoviesListFragment(moviesListCategory))
    }

    private fun navigateToMovieDetails(id: Int) {
        findNavController().navigate(HomeFragmentDirections.toMovieDetailsFragment(id))
    }

    private fun initViews() {
        binding.apply {
            popularMovies.movieListTitle.text = getString(R.string.popular_movies)
            topRatedMovies.movieListTitle.text = getString(R.string.top_rated_movies)
        }
    }

    private fun initClickListeners() {
        binding.apply {
            popularMovies.movieListError.retryButton.setOnClickListener { viewModel.getPopularMovies() }
            topRatedMovies.movieListError.retryButton.setOnClickListener { viewModel.getTopRatedMovies() }
        }
    }

    private fun setWelcomeMessage() {
        val now = LocalTime.now()
        binding.title.text = when {
            now.isAfter(LocalTime.of(4, 0)) and now.isBefore(LocalTime.of(12, 0)) -> {
                getString(R.string.good_morning)
            }
            now.isAfter(LocalTime.of(12, 0)) and now.isBefore(LocalTime.of(20, 0)) -> {
                getString(R.string.good_afternoon)
            }
            else -> {
                getString(R.string.good_evening)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}