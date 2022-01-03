package com.example.unsplashphotos.utils

data class DataState<T>(val status: Status, val data: T?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): DataState<T> {
            return DataState(
                Status.SUCCESS,
                data,
                null
            )
        }

        fun <T> error(message: String, data: T? = null): DataState<T> {
            return DataState(
                Status.ERROR,
                data,
                message
            )
        }

        fun <T> loading(data: T? = null): DataState<T> {
            return DataState(
                Status.LOADING,
                data,
                null
            )
        }
    }

    fun isSuccess() = status == Status.SUCCESS

    fun handleResult(handler: () -> Unit, defaultErrorData: T?): T? {
        if (isSuccess()) {
            handler()
            return data ?: defaultErrorData
        }
        return defaultErrorData
    }
}

