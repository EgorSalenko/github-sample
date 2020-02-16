package io.esalenko.github.sample.app.ui.common

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.*
import timber.log.Timber


abstract class BaseViewModel(app: Application) : AndroidViewModel(app) {

    private val commonViewModelJob = Job()
    private val viewModelScope = CoroutineScope(Dispatchers.IO + commonViewModelJob)

    fun executeOnBackground(
        body: suspend CoroutineScope.() -> Unit,
        error: (suspend (Exception) -> Unit)? = null
    ) {
        viewModelScope.launch {
            try {
                body.invoke(viewModelScope)
            } catch (e: Exception) {
                Timber.e(e)
                error?.invoke(e)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancelChildren()
    }
}
