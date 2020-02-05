package io.esalenko.github.sample.app.ui.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import timber.log.Timber


abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    private val searchJob = Job()
    private val searchScope = CoroutineScope(Dispatchers.IO + searchJob)

    fun safeExecute(
        body: suspend CoroutineScope.() -> Unit,
        error: (suspend (Exception) -> Unit)? = null
    ) {
        searchScope.launch {
            try {
                body.invoke(searchScope)
            } catch (e: Exception) {
                error?.invoke(e)
                Timber.e(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        searchScope.coroutineContext.cancelChildren()
    }

}