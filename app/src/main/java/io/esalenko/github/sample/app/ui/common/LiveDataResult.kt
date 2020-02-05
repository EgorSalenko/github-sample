package io.esalenko.github.sample.app.ui.common

sealed class LiveDataResult<T> {
    data class Success<T>(val data: T?) : LiveDataResult<T>()
    data class Error<T>(val msg: String) : LiveDataResult<T>()
    class Loading<T> : LiveDataResult<T>()
}