package io.esalenko.github.sample.app.data.db.dao

import androidx.room.*
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity

@Dao
interface SearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: SearchItemEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertList(items: List<SearchItemEntity>)

    @Query("SELECT * FROM search_items")
    suspend fun getAll(): List<SearchItemEntity>

    @Delete
    suspend fun delete(searchItemEntity: SearchItemEntity)

    @Query("DELETE FROM search_items")
    suspend fun clearAll()
}