package com.example.fintech2023chupin.ui.moviedetails.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.fintech2023chupin.R
import com.example.fintech2023chupin.app.FintechApp
import com.example.fintech2023chupin.databinding.FragmentMovieDetailsBinding
import com.example.fintech2023chupin.ui.lazyViewModel
import com.example.fintech2023chupin.ui.moviedetails.presentation.MovieDetailsViewModel
import com.example.fintech2023chupin.ui.movieslist.ui.MoviesListFragment

class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    private val viewModel: MovieDetailsViewModel by lazyViewModel { stateHandel ->
        (activity?.application as FintechApp).appComponent.injectMovieDetailsViewModel()
            .create(stateHandel)
    }
    private val binding: FragmentMovieDetailsBinding by viewBinding()
    private var modelId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity?.application as FintechApp).appComponent.inject(this)

        modelId = requireArguments().getInt(MoviesListFragment.KEY_ID)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.control(modelId!!)

        viewModel.movieLiveData.observe(viewLifecycleOwner) {

            binding.tvTitleInDetails.text = it.title
            binding.tvDescriptionInDetails.text = it.description
            val genre = it.genres.map {list -> list.genre }
            binding.tvGenresInDetails.text = "Жанры: $genre"
            val country = it.countries.map { list -> list.country }
            binding.tvCountriesInDetails.text = "Страны: $country"
            Glide.with(this@MovieDetailsFragment)
                .load(it.poster)
                .into(binding.imPosterInDetails)

        }

        binding.backDetails.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieDetailsFragment().apply {
            arguments = Bundle().apply {

            }
        }
    }
}