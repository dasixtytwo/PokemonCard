package com.da.pokemoncard.Activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.da.pokemoncard.Adapter.PokemonListAdapter
import com.da.pokemoncard.Common.Common
import com.da.pokemoncard.Common.ItemOffsetDecoration
import com.da.pokemoncard.R
import com.da.pokemoncard.Retrofit.IPokemonList
import com.da.pokemoncard.Retrofit.RetrofitClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class PokemonList : Fragment() {

    internal var compositeDisposable = CompositeDisposable()
    internal var iPokemonList:IPokemonList

    internal lateinit var recycler_view: RecyclerView

    init{
        val retrofit = RetrofitClient.instance
            iPokemonList = retrofit.create(IPokemonList::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val itemView = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        recycler_view = itemView.findViewById(R.id.pokemon_recyclerView) as RecyclerView
        recycler_view.setHasFixedSize(true)
        recycler_view.layoutManager = GridLayoutManager(activity!!, 2)
        val itemDecoration = ItemOffsetDecoration(activity!!,
            R.dimen.spacing
        )
        recycler_view.addItemDecoration(itemDecoration)

        fetchData()

        return itemView
    }

    private fun fetchData() {
        compositeDisposable.add(iPokemonList.listPokemon
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe{ pokemonDex ->
                Common.pokemonList = pokemonDex.pokemon!!
                val adapter = PokemonListAdapter(activity!!,Common.pokemonList)

                recycler_view.adapter =adapter
            }
        );
    }
}
