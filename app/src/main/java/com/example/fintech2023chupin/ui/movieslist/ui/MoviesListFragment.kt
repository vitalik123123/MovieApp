package com.example.fintech2023chupin.ui.movieslist.ui

import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fintech2023chupin.ConnectionType
import com.example.fintech2023chupin.NetworkMonitorUtil
import com.example.fintech2023chupin.R
import com.example.fintech2023chupin.app.FintechApp
import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import com.example.fintech2023chupin.databinding.FragmentMoviesListBinding
import com.example.fintech2023chupin.ui.lazyViewModel
import com.example.fintech2023chupin.ui.moviedetails.ui.MovieDetailsFragment
import com.example.fintech2023chupin.ui.movieslist.presentation.MoviesAdapter
import com.example.fintech2023chupin.ui.movieslist.presentation.MoviesListViewModel
import kotlinx.coroutines.launch

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MoviesListViewModel by lazyViewModel { stateHandel ->
        (activity?.application as FintechApp).appComponent.injectMoviesListViewModel()
            .create(stateHandel)
    }
    private val binding: FragmentMoviesListBinding by viewBinding()
    private var moviesAdapter: MoviesAdapter = MoviesAdapter()
    private lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var networkMonitor: NetworkMonitorUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as FintechApp).appComponent.inject(this)
        networkMonitor = NetworkMonitorUtil(requireContext())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()

        networkMonitor.result = { isAvailable, type ->
            requireActivity().runOnUiThread {
                when (isAvailable) {
                    true -> {
                        when (type) {
                            ConnectionType.Wifi -> {
                                Toast.makeText(context, " Test connection WiFi", Toast.LENGTH_LONG)
                                    .show()
                                connection()
                            }
                            ConnectionType.Cellular -> {
                                Toast.makeText(
                                    context,
                                    " Test connection Cellular",
                                    Toast.LENGTH_LONG
                                ).show()

                                connection()
                            }
                            else -> {}
                        }
                    }
                    false -> {
                        Toast.makeText(context, " Test connection No Connection", Toast.LENGTH_LONG)
                            .show()

                        disconnection()
                    }
                }
            }
        }

        moviesAdapter.setOnClickListener(onClick)
    }

    private fun initUi() {
        binding.rvMovies.adapter = moviesAdapter
        layoutManager = LinearLayoutManager(context)
        binding.rvMovies.layoutManager = layoutManager
        binding.tvTitleList.setText(R.string.text_popular)

    }

    private fun connection() {
        binding.tvTitleList.setText(R.string.text_popular)
        binding.layoutError.visibility = View.GONE
        binding.rvMovies.visibility = View.VISIBLE
        viewModel.fetchApi()
        viewModel.listPopularMoviesLiveData.observe(viewLifecycleOwner) { list ->
            moviesAdapter.setData(list)
        }
        binding.btnPopular.setOnClickListener {
            binding.layoutError.visibility = View.GONE
            binding.rvMovies.visibility = View.VISIBLE
            viewModel.fetchApi()
            binding.tvTitleList.setText(R.string.text_popular)
            viewModel.listPopularMoviesLiveData.observe(viewLifecycleOwner) { list ->
                moviesAdapter.setData(list)
            }
        }
        binding.btnFavorite.setOnClickListener {
            binding.layoutError.visibility = View.GONE
            binding.rvMovies.visibility = View.VISIBLE
            viewModel.getAllMoviesLocal()
            binding.tvTitleList.setText(R.string.text_favorite)
            viewModel.listFavoriteMoviesLiveData.observe(viewLifecycleOwner) { list ->
                moviesAdapter.setData(list)
            }
        }
    }

    private fun disconnection() {
        binding.tvTitleList.setText(R.string.text_popular)
        binding.layoutError.visibility = View.VISIBLE
        binding.rvMovies.visibility = View.GONE

        binding.btnFavorite.setOnClickListener {
            binding.layoutError.visibility = View.GONE
            binding.rvMovies.visibility = View.VISIBLE
            viewModel.getAllMoviesLocal()
            binding.tvTitleList.setText(R.string.text_favorite)
            viewModel.listFavoriteMoviesLiveData.observe(viewLifecycleOwner) { list ->
                moviesAdapter.setData(list)
            }
        }

        binding.btnPopular.setOnClickListener {
            binding.tvTitleList.setText(R.string.text_popular)
            binding.layoutError.visibility = View.VISIBLE
            binding.rvMovies.visibility = View.GONE
        }
    }

    private val onClick = object : MoviesAdapter.OnItemClickListener {
        override fun onClick(modelId: Int) {

            viewModel.saveMovieDetails(modelId)
            val fragment: Fragment
            val bundle = Bundle()
            bundle.putInt(KEY_ID, modelId)
            fragment = MovieDetailsFragment.newInstance()
            fragment.arguments = bundle

            activity!!.supportFragmentManager.beginTransaction()
                .setCustomAnimations(
                    android.R.anim.slide_in_left,
                    android.R.anim.fade_out,
                    android.R.anim.fade_in,
                    android.R.anim.slide_out_right
                )
                .replace(R.id.root, fragment).addToBackStack(fragment.javaClass.simpleName).commit()
        }

        override fun onLongClick(model: FilmTopResponse_films) {
            viewModel.saveMovieLocal(model)
        }
    }

    override fun onResume() {
        super.onResume()
        networkMonitor.register()
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MoviesListFragment().apply {
            arguments = Bundle().apply {

            }
        }

        const val KEY_ID = "id"
    }
}