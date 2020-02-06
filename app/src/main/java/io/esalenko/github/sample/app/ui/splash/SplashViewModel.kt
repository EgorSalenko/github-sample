package io.esalenko.github.sample.app.ui.splash

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.esalenko.github.sample.app.helper.OAuth2TokenHelper
import io.esalenko.github.sample.app.ui.common.BaseViewModel
import io.esalenko.github.sample.app.ui.common.Event
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import timber.log.Timber


class SplashViewModel(app: Application, private val tokenHelper: OAuth2TokenHelper) :
    BaseViewModel(app) {

    private val _tokenLiveData = MutableLiveData<Event<TokenResult>>()
    val tokenLiveData: LiveData<Event<TokenResult>>
        get() = _tokenLiveData

    fun validateToken() {
        safeExecute(
            {
                if (tokenHelper.hasToken) {
                val tokenResult = async {
                    tokenHelper.validateToken()
                }
                val user = tokenResult.await()
                Timber.i("Valid token for user = ${user.name}")
                _tokenLiveData.postValue(Event(TokenResult.Valid))
                } else {
                    _tokenLiveData.postValue(Event(TokenResult.Empty))
                }
                delay(3000)
            },
            {
                _tokenLiveData.postValue(Event(TokenResult.Invalid))
                Timber.e(it)
            }
        )
    }

    sealed class TokenResult {
        object Valid : TokenResult()
        object Invalid : TokenResult()
        object Empty : TokenResult()
    }
}
