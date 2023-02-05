package com.example.fintech2023chupin.ui.movieslist.presentation

import androidx.lifecycle.*
import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import com.example.fintech2023chupin.data.repositories.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class MoviesListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val compositeDisposable = io.reactivex.rxjava3.disposables.CompositeDisposable()

    private val listPopularMoviesLiveDataMutable = MutableLiveData<List<FilmTopResponse_films>>()
    val listPopularMoviesLiveData: LiveData<List<FilmTopResponse_films>> =
        listPopularMoviesLiveDataMutable

    private val listFavoriteMoviesLiveDataMutable = MutableLiveData<List<FilmTopResponse_films>>()
    val listFavoriteMoviesLiveData: LiveData<List<FilmTopResponse_films>> =
        listFavoriteMoviesLiveDataMutable

    init {
        fetchApi()
        getAllMoviesLocal()
    }

    fun fetchApi() {
        compositeDisposable.add(
            moviesRepository.getMoviesRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ content ->
                    listPopularMoviesLiveDataMutable.value = content.films
                    refreshPopularContent()
                }, {

                })
        )
    }

    fun getAllMoviesLocal() {
        compositeDisposable.add(
            moviesRepository.getMoviesLocal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    listFavoriteMoviesLiveDataMutable.value = list
                }, {})
        )
    }

    fun saveMovieLocal(film: FilmTopResponse_films) {
        viewModelScope.launch {
            moviesRepository.checkMovieLocal(film)
            saveMovieDetails(film.id)
        }
    }

    fun saveMovieDetails(id: Int) {
        compositeDisposable.add(
            moviesRepository.getMovieForId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewModelScope.launch {
                        moviesRepository.createMovieDetailsLocal(it)
                    }
                }, {

                })
        )
    }

    private fun refreshPopularContent() {
        viewModelScope.launch {
            listFavoriteMoviesLiveDataMutable.value?.map { top ->
                if (top.stateInFavorite) {
                    listPopularMoviesLiveDataMutable.value?.map { bottom ->
                        if (top.id == bottom.id) {
                            bottom.stateInFavorite = true
                        }
                    }
                }
            }
        }
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