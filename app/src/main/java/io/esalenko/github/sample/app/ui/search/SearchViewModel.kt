package io.esalenko.github.sample.app.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity
import io.esalenko.github.sample.app.data.repository.SearchRepository
import io.esalenko.github.sample.app.ui.common.LiveDataResult
import kotlinx.coroutines.*
import timber.log.Timber


class SearchViewModel(app: Application, private val repository: SearchRepository) :
    AndroidViewModel(app) {

    private val _searchLiveData = MutableLiveData<LiveDataResult<List<SearchItemEntity>>>()
    val searchLiveData: LiveData<LiveDataResult<List<SearchItemEntity>>>
        get() = _searchLiveData

    private val searchJob = Job()
    private val searchScope = CoroutineScope(Dispatchers.IO + searchJob)

    private var lastQuery: String = ""

    init {
        fetchMore()
    }

    fun search(query: String) {
        if (!isQueySame(query)) {
            _searchLiveData.postValue(LiveDataResult.Loading())
            lastQuery = query
            searchScope.launch {
                try {
                    repository.clearAll()
                    repository.search(query, 1)
                    val searItems = async {
                        repository.getAll()
                    }
                    val list = searItems.await()
                    _searchLiveData.postValue(LiveDataResult.Success(list))
                } catch (e: Exception) {
                    _searchLiveData.postValue(LiveDataResult.Error("Error occurred while fetching data"))
                    Timber.e(e)
                }
            }
        }
    }

    fun onLoadMore(nextPage: Int) {
        if (lastQuery.isNotEmpty()) {
            searchScope.launch {
                repository.search(lastQuery, nextPage)
            }
        }
    }

    private fun fetchMore() {
        searchScope.launch {
            try {
                val searchItems = async {
                    repository.getAll()
                }
                _searchLiveData.postValue(LiveDataResult.Success(searchItems.await()))
            } catch (e: Exception) {
                Timber.e(e)
                _searchLiveData.postValue(LiveDataResult.Error("Error occurred while fetching data"))
            }
        }
    }

    private fun isQueySame(query: String): Boolean {
        return query == lastQuery
    }

    override fun onCleared() {
        super.onCleared()
        searchScope.coroutineContext.cancelChildren()
    }
}
