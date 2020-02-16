package io.esalenko.github.sample.app.ui.user

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.esalenko.github.sample.app.data.model.user.User
import io.esalenko.github.sample.app.data.repository.UserRepository
import io.esalenko.github.sample.app.ui.common.BaseViewModel
import io.esalenko.github.sample.app.ui.common.LiveDataResult
import kotlinx.coroutines.async
import timber.log.Timber


class UserViewModel(app: Application, private val repository: UserRepository) : BaseViewModel(app) {

    val userLiveData: LiveData<LiveDataResult<User>>
        get() = _userLiveData
    private val _userLiveData = MutableLiveData<LiveDataResult<User>>()

    init {
        getUserInfo()
    }

    private fun getUserInfo() {
        _userLiveData.postValue(LiveDataResult.Loading())
        executeOnBackground(
            {
                val user = async { repository.getUserData() }
                _userLiveData.postValue(LiveDataResult.Success(user.await()))
            },
            { error ->
                Timber.e(error)
                _userLiveData.postValue(LiveDataResult.Error("Unable to fetch user data"))
            }
        )
    }
}
