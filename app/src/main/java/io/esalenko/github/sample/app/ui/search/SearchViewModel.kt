package io.esalenko.github.sample.app.ui.search

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity
import io.esalenko.github.sample.app.data.repository.SearchRepository
import io.esalenko.github.sample.app.ui.common.BaseViewModel
import io.esalenko.github.sample.app.ui.common.LiveDataResult
import kotlinx.coroutines.async


class SearchViewModel(app: Application, private val repository: SearchRepository) :
    BaseViewModel(app) {

    private val _searchLiveData = MutableLiveData<LiveDataResult<List<SearchItemEntity>>>()
    val searchLiveData: LiveData<LiveDataResult<List<SearchItemEntity>>>
        get() = _searchLiveData

    private var lastQuery: String = ""

    companion object {
        private const val FIRST_PAGE = 1
    }

    init {
        fetchCachedData()
    }

    fun search(query: String) {
        if (!isQuerySame(query)) { // optimization for avoiding spam
            _searchLiveData.postValue(LiveDataResult.Loading())
            lastQuery = query
            clearCache()
            getSearchedList(FIRST_PAGE, { searchItems ->
                _searchLiveData.postValue(LiveDataResult.Success(searchItems))
            }, {
                _searchLiveData.postValue(LiveDataResult.Error("Error occurred while fetching data"))
            })
        }
    }

    fun onLoadMore(nextPage: Int) {
        if (lastQuery.isNotEmpty()) {
            getSearchedList(nextPage, { searchItems ->
                _searchLiveData.postValue(LiveDataResult.Success(searchItems))
            }, {
                _searchLiveData.postValue(LiveDataResult.Error("Error occurred while fetching data"))
            })
        } else {
            _searchLiveData.postValue(LiveDataResult.Cache())
        }
    }

    fun deleteItem(id: Int) {
        delete(id)
        fetchCachedData()
    }

    private fun delete(id: Int) {
        safeExecute({
            repository.delete(id)
        })
    }

    private fun getSearchedList(
        page: Int,
        onExecute: suspend (List<SearchItemEntity>) -> Unit,
        onError: suspend (Exception) -> Unit
    ) {
        safeExecute(
            {
                repository.search(lastQuery, page)
                val searchItems = async {
                    repository.getAll()
                }
                onExecute.invoke(searchItems.await())
            },
            { error ->
                onError.invoke(error)
            })

    }

    private fun clearCache() {
        safeExecute({ repository.clearAll() })
    }

    private fun fetchCachedData() {
        safeExecute({
            val searchItems = async {
                repository.getAll()
            }
            _searchLiveData.postValue(LiveDataResult.Success(searchItems.await()))
        }, {
            _searchLiveData.postValue(LiveDataResult.Error("Error occurred while fetching data"))
        })
    }

    private fun isQuerySame(query: String): Boolean {
        return query == lastQuery
    }
}
