package io.esalenko.github.sample.app.data.repository

import io.esalenko.github.sample.app.data.db.dao.SearchDao
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity
import io.esalenko.github.sample.app.data.model.Items
import io.esalenko.github.sample.app.data.network.SearchService
import kotlinx.coroutines.*


class SearchRepository(private val service: SearchService, private val searchDao: SearchDao) {

    private val networkJob = Job()
    private val networkScope = CoroutineScope(Dispatchers.IO + networkJob)

    companion object {
        private const val PAGE_LIMIT = 30
    }

    fun search(query: String, sort: String, order: String) {
        networkScope.launch {
            val response = async {
                service.search(query, sort, order, PAGE_LIMIT).items
            }
            searchDao.insertList(response.await().toSearchItemEntityList())
        }
    }

    suspend fun getAll(): List<SearchItemEntity> {
        return searchDao.getAll()
    }

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
}
