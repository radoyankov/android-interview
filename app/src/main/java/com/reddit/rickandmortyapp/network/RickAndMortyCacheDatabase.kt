package com.reddit.rickandmortyapp.network

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity(tableName = "characters_cache")
data class CachedCharactersResponse(
    @PrimaryKey val page: Int,
    val json: String
)

@Dao
interface CharactersCacheDao {
    @Query("SELECT * FROM characters_cache WHERE page = :page")
    suspend fun getCachedCharacters(page: Int): CachedCharactersResponse?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCache(cachedResponse: CachedCharactersResponse)
}

@Database(entities = [CachedCharactersResponse::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun charactersCacheDao(): CharactersCacheDao
}