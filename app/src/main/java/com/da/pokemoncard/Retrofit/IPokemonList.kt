package com.da.pokemoncard.Retrofit

import com.da.pokemoncard.Model.Pokemondex
import io.reactivex.Observable
import retrofit2.http.GET

interface IPokemonList {
    @get:GET("pokedex.json")
    val listPokemon:Observable<Pokemondex>
}