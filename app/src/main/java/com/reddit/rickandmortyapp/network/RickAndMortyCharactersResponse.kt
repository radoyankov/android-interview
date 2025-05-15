package com.reddit.rickandmortyapp.network

import java.io.Serializable

// link to API Documentation: https://rickandmortyapi.com/documentation#character

data class RickAndMortyCharactersResponse(
    val info: RickAndMortyMetadata,
    val results: List<RickAndMortyCharacter>
)

data class RickAndMortyMetadata(val next: String?)

data class RickAndMortyCharacter(
    val image: String,
    val name: String,
    val species: String,
    val gender: String,
    val url: String,
    val origin: RickAndMortyCharacterOrigin,
    val location: RickAndMortyCharacterLocation
) : Serializable

data class RickAndMortyCharacterOrigin(
    val name: String
) : Serializable

data class RickAndMortyCharacterLocation(
    val name: String
) : Serializable