package com.example.fintech2023chupin.ui.movieslist.presentation

import androidx.lifecycle.*
import com.example.fintech2023chupin.data.model.FilmTopResponse_films
import com.example.fintech2023chupin.data.repositories.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MoviesListViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val compositeDisposable = io.reactivex.rxjava3.disposables.CompositeDisposable()

    val state = MutableStateFlow(value = MyState())

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
                    state.update { ui -> ui.copy(listPopularMovies = content.films) }
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
                    state.update { ui -> ui.copy(listFavoriteMovies = list) }
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
            state.value.listFavoriteMovies.map { top ->
                if (top.stateInFavorite) {
                    state.value.listPopularMovies.map { bottom ->
                        if (top.id == bottom.id) {
                            bottom.stateInFavorite = true
                        }
                    }
                }
            }
        }
    }

    fun changeStateChoice(currentState: Boolean) {
        state.update { ui ->
            ui.copy(stateChoice = currentState)
        }
    }

    fun changeStateNetwork(currentState: Boolean){
        state.update {ui ->
            ui.copy(stateNetwork = currentState)
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

    data class MyState(
        val stateChoice: Boolean = true,
        val stateNetwork: Boolean = true,
        val listFavoriteMovies: List<FilmTopResponse_films> = emptyList(),
        val listPopularMovies: List<FilmTopResponse_films> = emptyList()
    )
}