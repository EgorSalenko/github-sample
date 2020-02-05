package io.esalenko.github.sample.app.data.repository

import io.esalenko.github.sample.app.data.db.dao.SearchDao
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity
import io.esalenko.github.sample.app.data.model.Items
import io.esalenko.github.sample.app.data.network.SearchService


class SearchRepository(private val service: SearchService, private val searchDao: SearchDao) {

    companion object {
        private const val PAGE_LIMIT = 30
    }

    suspend fun search(query: String, page: Int) {
        val items = service.search(
            query,
            sort = "stars",
            order = "desc",
            limit = PAGE_LIMIT,
            page = page
        ).items
        if (items.isNotEmpty()) {
            searchDao.insertList(items.toSearchItemEntityList())
        }
    }

    suspend fun getAll(): List<SearchItemEntity> = searchDao.getAll()

    private fun List<Items>.toSearchItemEntityList(): List<SearchItemEntity> {
        val entities = mutableListOf<SearchItemEntity>()
        this.forEach { item ->
            entities.add(item.toSearchItemEntity())
        }
        return entities
    }

    private fun Items.toSearchItemEntity(): SearchItemEntity {
        return SearchItemEntity(
            this.id,
            this.full_name,
            this.description,
            this.url,
            this.stargazers_count,
            this.language
        )
    }

    suspend fun delete(id: Int) {
        searchDao.delete(id)
    }

    suspend fun clearAll() {
        searchDao.clearAll()
    }
}
