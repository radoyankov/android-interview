package com.reddit.rickandmortyapp.network

// link to API Documentation: https://rickandmortyapi.com/documentation#character

data class RickAndMortyCharactersResponse(
    val info: RickAndMortyMetadata,
    val results: List<RickAndMortyCharacter>
)

data class RickAndMortyMetadata(val next: String?)

data class RickAndMortyCharacter(
    val image: String,
    val name: String,
    val url: String,
    val origin: RickAndMortyCharacterOrigin
)

data class RickAndMortyCharacterOrigin(
    val name: String
)