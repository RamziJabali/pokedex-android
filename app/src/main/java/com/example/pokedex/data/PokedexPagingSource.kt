package com.example.pokedex.data

import android.annotation.SuppressLint
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.pokedex.model.PokedexRepo
import com.example.pokedex.model.UseCase
import com.example.pokedex.network.Pokedex.Pokedex
import com.example.pokedex.pokedex.view.GridProperties
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

private const val POKEMON_STARTING_POSITION = 0

class PokedexPagingSource(private val pokedexUseCase: UseCase) :
    RxPagingSource<Int, GridProperties>() {
    companion object {
        private const val LIMIT = 10
    }

    val compositeDisposable = CompositeDisposable()
    override fun getRefreshKey(state: PagingState<Int, GridProperties>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    @SuppressLint("CheckResult")
    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, GridProperties>> {
        val position = params.key ?: POKEMON_STARTING_POSITION
        return try {
            val pokedexList = mutableListOf<GridProperties>()
            pokedexUseCase.getPokedex(position, LIMIT)
                .subscribeOn(Schedulers.io())
                .map { pokedex ->
                    Log.i(PagingSource::class.toString(), "Got the pokedex")
                    for (i in pokedex.pokemonId.indices) {
                        val gridProperties = GridProperties(
                            itemText = pokedex.pokemonName[i],
                            itemOrderNumberString = pokedex.pokemonIdString[i],
                            itemOrderNumber = pokedex.pokemonId[i]
                        )
                        pokedexList.add(gridProperties)
                    }
                    LoadResult.Page(
                        data = pokedexList,
                        prevKey = if (position == POKEMON_STARTING_POSITION) null else position - 1,
                        nextKey = position + LIMIT
                    )
                }
        } catch (exception: IOException) {
            Single.just(LoadResult.Error(exception))
        } catch (exception: HttpException) {
            Single.just(LoadResult.Error(exception))
        }
    }
}