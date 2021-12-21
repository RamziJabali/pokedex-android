package com.example.pokedex.pokedex.view

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.VisibleForTesting
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokedex.R
import com.example.pokedex.pokemon.view.PokemonActivity
import com.example.pokedex.pokedex.viewmodel.ViewModel
import com.example.pokedex.pokedex.viewmodel.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), ViewListener {

    private val recyclerListView: RecyclerView by lazy {
        findViewById(R.id.recycler_list_view)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val pokedexRecyclerViewAdapter: PokedexAdapter = PokedexAdapter { position ->
        viewModel.launchActivity(position)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: ViewModel by viewModel()

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    var viewState: ViewState = ViewState()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setup()
        viewModel.startApplication()
        compositeDisposable.add(viewModel.viewStateObservable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { viewState ->
                this.viewState = viewState
                setNewViewState(viewState)
            }
        )
    }

    override fun setNewViewState(viewState: ViewState) {
        if (viewState.launchActivity) {
            viewState.launchActivity = false
            startActivity(
                PokemonActivity.newInstance(this, viewState.pokeId),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun setup() {
        recyclerListView.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = pokedexRecyclerViewAdapter
        }
        viewModel.pokedexPager
            .subscribe { pagingData ->
                pokedexRecyclerViewAdapter.submitData(lifecycle, pagingData)
            }.addTo(compositeDisposable)
    }
}