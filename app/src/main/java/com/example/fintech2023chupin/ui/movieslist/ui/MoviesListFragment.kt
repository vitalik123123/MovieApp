package com.example.fintech2023chupin.ui.movieslist.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.fintech2023chupin.R
import com.example.fintech2023chupin.app.FintechApp
import com.example.fintech2023chupin.databinding.FragmentMoviesListBinding
import com.example.fintech2023chupin.ui.lazyViewModel
import com.example.fintech2023chupin.ui.moviedetails.ui.MovieDetailsFragment
import com.example.fintech2023chupin.ui.movieslist.presentation.MoviesAdapter
import com.example.fintech2023chupin.ui.movieslist.presentation.MoviesListViewModel

class MoviesListFragment : Fragment(R.layout.fragment_movies_list) {

    private val viewModel: MoviesListViewModel by lazyViewModel { stateHandel ->
        (activity?.application as FintechApp).appComponent.injectMoviesListViewModel().create(stateHandel)
    }
    private val binding: FragmentMoviesListBinding by viewBinding()
    private var moviesAdapter: MoviesAdapter = MoviesAdapter()
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (activity?.application as FintechApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMovies.adapter = moviesAdapter
        layoutManager = LinearLayoutManager(context)
        binding.rvMovies.layoutManager = layoutManager

        viewModel.listMoviesLiveData.observe(viewLifecycleOwner) {
            moviesAdapter.setData(it)
        }

        moviesAdapter.setOnClickListener(onClick)
    }

    private val onClick = object : MoviesAdapter.OnItemClickListener{
        override fun onClick(modelId: Int) {

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