package com.kjurkovic.common.async

/**
 * The T generic is unused for some classes but since it is sealed and useful for Success and Fail,
 * it should be on all of them.
 *
 * Complete: Success, Fail
 * ShouldLoad: Uninitialized, Fail
 */
sealed class  Async<out T>(val complete: Boolean, val shouldLoad: Boolean, private val value: T?) {

    /**
     * Returns the value or null.
     *
     * Success always have a value. Loading and Fail can also return a value which is useful for
     * pagination or progressive data loading.
     *
     * Can be invoked as an operator like: `yourProp()`
     */
    open operator fun invoke(): T? = value
    fun isSuccess() = value != null
}

object Uninitialized : Async<Nothing>(complete = false, shouldLoad = true, value = null)
data class Fail<out T>(val value: T? = null) : Async<Throwable>(complete = true, shouldLoad = true, value = null)
data class Loading<out T>(val value: T? = null) : Async<T>(complete = false, shouldLoad = false, value = value)
data class Success<out T>(val value: T) : Async<T>(complete = true, shouldLoad = false, value = value) {
    override operator fun invoke(): T = value
}
