package com.reddit.rickandmortyapp.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RickAndMortyService {
    private val moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val api: RickAndMortyApi = retrofit.create(RickAndMortyApi::class.java)
}

interface RickAndMortyApi {
    @GET("character")
    suspend fun fetchCharactersCoroutine(@Query("page") page: Int): RickAndMortyCharactersResponse
}

class RickAndMortyRepository(
    private val api: RickAndMortyApi,
    private val cacheDao: CharactersCacheDao,
    moshi: Moshi = Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
) {
    private val adapter = moshi.adapter(RickAndMortyCharactersResponse::class.java)

    suspend fun getCharactersResult(page: Int): RickAndMortyCharactersResponse {
        return try {
            val response = api.fetchCharactersCoroutine(page)
            val json = adapter.toJson(response)
            cacheDao.insertCache(CachedCharactersResponse(page, json))
            response
        } catch (e: Exception) {
            cacheDao.getCachedCharacters(page)?.let {
                adapter.fromJson(it.json) ?: throw e
            } ?: throw e
        }
    }
}