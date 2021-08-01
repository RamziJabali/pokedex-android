package com.example.pokedex.pokedex.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.GONE
import android.widget.FrameLayout
import android.widget.GridView
import com.example.pokedex.R
import com.example.pokedex.pokemon.view.PokemonFragment
import com.example.pokedex.pokedex.viewmodel.ViewModel
import com.example.pokedex.pokedex.viewmodel.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ViewListener {

    private lateinit var gridView: GridView
    private lateinit var gridViewAdapter: PokedexGridAdapter
    private lateinit var flFragment: FrameLayout
    private lateinit var pokemonFragment: PokemonFragment

    private lateinit var viewState: ViewState

    private val compositeDisposable = CompositeDisposable()

    private val viewModel: ViewModel by viewModel()

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
        supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, pokemonFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun setNewViewState(viewState: ViewState) {
        gridViewAdapter.setGridItems(viewState.gridProperties)
    }


    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    private fun setup() {
        setupFragment()
        gridView = findViewById(R.id.gridView)
        gridView.numColumns = 2
        gridViewAdapter = PokedexGridAdapter()
        gridView.adapter = gridViewAdapter
        onItemClick()
    }

    private fun setupFragment() {
        pokemonFragment = PokemonFragment()
        flFragment = findViewById(R.id.flFragment)
    }

    private fun onItemClick() {
        gridView.setOnItemClickListener { parent, view, position, id ->
            gridView.visibility = GONE
            pokemonFragment.setFragmentMembers(viewState, position)
            supportFragmentManager.beginTransaction()
                .replace(R.id.flFragment, pokemonFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}