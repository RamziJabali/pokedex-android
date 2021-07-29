package com.example.pokedex.pokedexview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridView
import com.example.pokedex.R
import com.example.pokedex.pokemonview.PokemonActivity
import com.example.pokedex.viewmodelpokedex.ViewModel
import com.example.pokedex.viewmodelpokedex.ViewState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), ViewListener {

    private lateinit var gridView: GridView
    private lateinit var gridViewAdapter: PokedexGridAdapter
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
            .subscribe { viewState -> setNewViewState(viewState) }
        )
    }

    override fun setNewViewState(viewState: ViewState) {
        gridViewAdapter.setGridItems(viewState.gridProperties)
    }


    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun setup() {
        gridView = findViewById(R.id.gridView)
        gridView.numColumns = 2
        gridViewAdapter = PokedexGridAdapter()
        gridView.setOnItemClickListener { parent, view, position, id ->
            startActivity(Intent(this, PokemonActivity::class.java))
        }
        gridView.adapter = gridViewAdapter
    }


}