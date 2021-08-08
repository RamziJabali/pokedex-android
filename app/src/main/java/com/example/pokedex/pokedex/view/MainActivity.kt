package com.example.pokedex.pokedex.view

import android.app.ActivityOptions
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import androidx.annotation.VisibleForTesting
import com.example.pokedex.R
import com.example.pokedex.pokemon.view.PokemonActivity
import com.example.pokedex.pokedex.viewmodel.ViewModel
import com.example.pokedex.pokedex.viewmodel.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(), ViewListener {

    private lateinit var gridView: GridView

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    lateinit var gridViewAdapter: PokedexGridAdapter

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val viewModel: ViewModel by viewModel()
    private var viewState: ViewState = ViewState()
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
        gridViewAdapter.setGridItems(viewState.gridProperties)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun setup() {
        gridView = findViewById(R.id.gridView)
        gridView.numColumns = 2
        gridViewAdapter = PokedexGridAdapter()
        gridView.adapter = gridViewAdapter
        onItemClick()
    }

    private fun onItemClick() {
        gridView.setOnItemClickListener { parent, view, position, id ->
            val pokeId = viewState.gridProperties[position].itemOrderNumber
            startActivity(
                PokemonActivity.newInstance(this, pokeId),
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            )
        }
    }
}