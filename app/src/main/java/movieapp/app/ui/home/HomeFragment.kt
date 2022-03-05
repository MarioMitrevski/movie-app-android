package movieapp.app.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import movieapp.app.R
import movieapp.app.databinding.FragmentHomeBinding
import movieapp.app.util.MarginItemDecoration
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
        setLiveDataObservers()

        viewModel.getPopularMovies()
    }

    private fun setLiveDataObservers() {
        viewModel.popularMoviesLiveData.observe(viewLifecycleOwner) {
            binding.popularReleases.movieListRecyclerView.apply {
                adapter = HorizontalMovieListAdapter(it) {

                }
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

    private fun initViews() {
        binding.popularReleases.apply {
            movieListTitle.text = getString(R.string.popular_releases)
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