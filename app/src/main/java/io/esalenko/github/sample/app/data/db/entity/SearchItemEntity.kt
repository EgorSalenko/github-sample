package io.esalenko.github.sample.app.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_items")
data class SearchItemEntity(
    @PrimaryKey val id: Int,
    val full_name: String,
    val description: String?,
    val url: String,
    val stargazers_count: Int,
    val language: String?
)