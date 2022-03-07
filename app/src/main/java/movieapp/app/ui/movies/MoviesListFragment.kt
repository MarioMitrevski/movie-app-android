package movieapp.app.ui.movies

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import movieapp.app.R
import movieapp.app.databinding.FragmentMoviesListBinding
import movieapp.app.domain.movies.entities.MoviesListCategory
import movieapp.app.util.AutoDisposable
import movieapp.app.util.MarginItemDecoration
import movieapp.app.util.addTo

@AndroidEntryPoint
class MoviesListFragment : Fragment() {
    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private val args: MoviesListFragmentArgs by navArgs()
    private val viewModel: MoviesListViewModel by viewModels()

    private var movieListAdapter: MoviesListPagingAdapter? = null

    private val autoDisposable = AutoDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autoDisposable.bindTo(lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setToolbar()
        viewModel.initMoviesForCategoryFlowable(args.movieListCategory)
        initMoviesRecyclerView()
        setMoviesForCategoryLiveDataObserver()
    }

    private fun setToolbar() {
        binding.toolbar.apply {
            backButton.setOnClickListener { findNavController().popBackStack() }
            title.text = when (args.movieListCategory) {
                MoviesListCategory.POPULAR -> {
                    getString(R.string.popular_movies)
                }
                MoviesListCategory.TOP_RATED -> {
                    getString(R.string.top_rated_movies)
                }
            }
        }
    }

    private fun initMoviesRecyclerView() {
        movieListAdapter = MoviesListPagingAdapter(
            MovieItemDataComparator
        ) {
            findNavController().navigate(MoviesListFragmentDirections.toMovieDetailsFragment(it.id))
        }

        binding.moviesRecyclerView.apply {
            adapter = movieListAdapter?.withLoadStateFooter(LoadStateAdapterImpl())
            addItemDecoration(
                MarginItemDecoration(
                    verticalSpaceSize = resources.getDimensionPixelSize(R.dimen.margin_medium)
                )
            )
        }
    }

    private fun setMoviesForCategoryLiveDataObserver() {
        viewModel.apply {
            moviesForCategoryFlowable
                .subscribe { pagingData ->
                    movieListAdapter?.submitData(
                        viewLifecycleOwner.lifecycle,
                        pagingData
                    )
                }
                .addTo(autoDisposable)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}