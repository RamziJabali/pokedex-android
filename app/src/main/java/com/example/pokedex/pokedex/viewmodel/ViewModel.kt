package com.example.pokedex.pokedex.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import androidx.paging.rxjava2.flowable
import com.example.pokedex.BuildConfig
import com.example.pokedex.data.PokedexPagingSource
import com.example.pokedex.model.BasePokedex
import com.example.pokedex.model.PokedexRepo
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.Pokedex.Pokedex
import com.example.pokedex.pokedex.view.GridProperties
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.concurrent.Flow

class ViewModel(private var useCase: UseCase) : ViewModel(), KoinComponent {

    val viewStateObservable = BehaviorSubject.create<ViewState>()
    val pokedexPager = Pager(
        PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
        pagingSourceFactory = { PokedexPagingSource(useCase) }
    ).flowable

    private var viewState = ViewState()
    private var compositeDisposable = CompositeDisposable()
    
    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
 
    private fun invalidateView() {
        viewStateObservable.onNext(viewState)
    }

    fun launchActivity(itemOrderNumber: Int) {
        viewState.launchActivity = true
        viewState.pokeId = itemOrderNumber +1
        invalidateView()
    }

}