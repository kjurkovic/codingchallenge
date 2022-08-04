package com.kjurkovic.networking.core.handlers

import com.kjurkovic.common.wrappers.dispatchers.DispatcherProvider
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.inject.Singleton

interface ApiHandler {
    suspend fun <T> handleCall(call: suspend () -> Response<T>): T
    val networkError: StateFlow<Boolean>
}

@Singleton
class ApiHandlerImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
) : ApiHandler {

    private val _networkError = MutableStateFlow(false)
    override val networkError = _networkError.asStateFlow()

    override suspend fun <T> handleCall(call: suspend () -> Response<T>): T =
        withContext(dispatcherProvider.io()) {
            try {
                return@withContext handleCallInternal(call)
            } catch (ex: ApiException) {
                throw ex
            }
        }


    private suspend fun <T> handleCallInternal(call: suspend () -> Response<T>): T {
        try {
            val response = handleResponse(call())
            _networkError.value = false
            return response
        } catch (ex: Exception) {
            if (ex is UnknownHostException || ex is SocketTimeoutException) {
                Timber.i(ex, "Network Error")
                _networkError.value = true
            } else {
                Timber.d(ex)
                if (ex is ApiException) {
                    _networkError.value = false
                }
            }
            throw ex
        }
    }

    private fun <T> handleResponse(response: Response<T>): T {
        if (!response.isSuccessful) {
            throw ApiException(response)
        }
        return response.body() ?: throw ApiException(response)
    }
}
