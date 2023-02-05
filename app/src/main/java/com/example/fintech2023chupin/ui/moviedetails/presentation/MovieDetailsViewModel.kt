package com.example.fintech2023chupin.ui.moviedetails.presentation

import androidx.lifecycle.*
import com.example.fintech2023chupin.data.model.Film
import com.example.fintech2023chupin.data.repositories.MoviesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.launch

class MovieDetailsViewModel @AssistedInject constructor(
    @Assisted savedStateHandle: SavedStateHandle,
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val compositeDisposable = io.reactivex.rxjava3.disposables.CompositeDisposable()
    private val movieLiveDataMutable = MutableLiveData<Film>()
    val movieLiveData: LiveData<Film> = movieLiveDataMutable

    fun control(id: Int) {
        viewModelScope.launch {
            if (moviesRepository.existsMovieDetailsLocal(id)) {
                showMovieDetailsFromDatabase(id)
            } else {
                showMovieDetailsFromApi(id)
            }
        }
    }

    private fun showMovieDetailsFromApi(id: Int) {
        compositeDisposable.add(
            moviesRepository.getMovieForId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    movieLiveDataMutable.value = it
                }, {

                })
        )
    }

    private fun showMovieDetailsFromDatabase(id: Int) {
        compositeDisposable.add(
            moviesRepository.getForIdMovieDetailsLocal(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    list.map {
                        movieLiveDataMutable.value = it
                    }
                }, {})
        )
    }

    @AssistedFactory
    interface Factory {
        fun create(savedStateHandle: SavedStateHandle): MovieDetailsViewModel
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}