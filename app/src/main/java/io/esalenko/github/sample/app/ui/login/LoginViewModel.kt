package io.esalenko.github.sample.app.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.esalenko.github.sample.app.data.auth.OAuth2TokenHelper
import io.esalenko.github.sample.app.ui.common.Event
import net.openid.appauth.AuthState


class LoginViewModel(
    app: Application,
    private val tokenHelper: OAuth2TokenHelper
) : AndroidViewModel(app) {

    private val _screenStateLiveData = MutableLiveData<Event<Boolean>>()
    val screenStateLiveData: LiveData<Event<Boolean>>
        get() = _screenStateLiveData

    fun persistAuthState(authState: AuthState) {
        tokenHelper.persistAuthToken(authState)
        _screenStateLiveData.postValue(Event(true))
    }
}
