package io.esalenko.github.sample.app.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.esalenko.github.sample.app.helper.OAuth2TokenHelper
import io.esalenko.github.sample.app.ui.common.Event
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SplashViewModel(app: Application, private val tokenHelper: OAuth2TokenHelper) :
    AndroidViewModel(app) {

    private val _tokenLiveData = MutableLiveData<Event<TokenResult>>()
    val tokenLiveData: LiveData<Event<TokenResult>>
        get() = _tokenLiveData

    fun validateToken() {
        val tokenResult = GlobalScope.async {
            return@async if (tokenHelper.hasToken) {
                Event(TokenResult.Valid)
            } else {
                Event(TokenResult.Empty)
            }
        }
        GlobalScope.launch {
            delay(3000)
            _tokenLiveData.postValue(tokenResult.await())
        }
    }

    sealed class TokenResult {
        object Valid : TokenResult()
        object Empty : TokenResult()
    }
}