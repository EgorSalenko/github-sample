package io.esalenko.github.sample.app.ui.search

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.esalenko.github.sample.app.data.db.entity.SearchItemEntity
import io.esalenko.github.sample.app.data.repository.SearchRepository
import io.esalenko.github.sample.app.ui.common.LiveDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class SearchViewModel(app: Application, private val repository: SearchRepository) :
    AndroidViewModel(app) {

    private val _searchLiveData = MutableLiveData<LiveDataResult<List<SearchItemEntity>>>()
    val searchLiveData: LiveData<LiveDataResult<List<SearchItemEntity>>>
        get() = _searchLiveData

    private val fetchDbJob = Job()
    private val fetchDbScope = CoroutineScope(Dispatchers.IO + fetchDbJob)

    init {
        fetchMore()
    }

    fun search(query: String) {
        _searchLiveData.postValue(LiveDataResult.Loading())
        repository.search(
            query,
            sort = "stars",
            order = "desc"
        )
        fetchMore()
    }

    private fun fetchMore() {
        fetchDbScope.launch {
            try {
                _searchLiveData.postValue(LiveDataResult.Success(repository.getAll()))
            } catch (e: Exception) {
                _searchLiveData.postValue(LiveDataResult.Error("Error occurred while fetching data"))
            }
        }
    }
}
