package com.example.fintech2023chupin.ui.movieslist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.fintech2023chupin.data.model.FilmTopResponse
import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import com.example.fintech2023chupin.data.repositories.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

class MoviesListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val compositeDisposable = io.reactivex.rxjava3.disposables.CompositeDisposable()
    private val listMoviesLiveDataMutable = MutableLiveData<List<FilmTopResponse_films>>()
    val listMoviesLiveData: LiveData<List<FilmTopResponse_films>> = listMoviesLiveDataMutable

    init {
        fetchApi()
    }

    private fun fetchApi() {
        compositeDisposable.add(
            moviesRepository.getMoviesRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    listMoviesLiveDataMutable.value = it.films
                }, {

                })
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): MoviesListViewModel
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}