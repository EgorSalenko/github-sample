package io.esalenko.github.sample.app.ui.splash

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.esalenko.github.sample.app.data.auth.OAuth2TokenHelper
import io.esalenko.github.sample.app.ui.common.BaseViewModel
import io.esalenko.github.sample.app.ui.common.SingleEvent
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import timber.log.Timber


class SplashViewModel(app: Application, private val tokenHelper: OAuth2TokenHelper) :
    BaseViewModel(app) {

    private val _tokenLiveData = MutableLiveData<SingleEvent<TokenResult>>()
    val tokenLiveData: LiveData<SingleEvent<TokenResult>>
        get() = _tokenLiveData

    fun validateToken() {
        executeOnBackground(
            {
                if (tokenHelper.hasToken) {
                    val tokenResult = async { tokenHelper.validateToken() }
                    val user = tokenResult.await()
                    Timber.i("Valid token for user = ${user.name}")
                    _tokenLiveData.postValue(SingleEvent(TokenResult.Valid))
                } else {
                    _tokenLiveData.postValue(SingleEvent(TokenResult.Empty))
                }
                delay(3000)
            },
            { error ->
                Timber.e(error)
                _tokenLiveData.postValue(SingleEvent(TokenResult.Invalid))
            }
        )
    }

    sealed class TokenResult {
        object Valid : TokenResult()
        object Invalid : TokenResult()
        object Empty : TokenResult()
    }
}
