package com.kjurkovic.common.mvvm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kjurkovic.common.async.Async
import com.kjurkovic.common.async.Fail
import com.kjurkovic.common.async.Loading
import com.kjurkovic.common.async.Success
import com.kjurkovic.common.wrappers.dispatchers.DispatcherProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.reflect.KProperty1

abstract class BaseViewModel<ViewState, Action, SideEffect>(
    private val dispatcherProvider: DispatcherProvider,
    viewState: ViewState,
): ViewModel() {

    protected val _viewState: MutableStateFlow<ViewState> = MutableStateFlow(viewState)
    val viewState = _viewState.asStateFlow()

    val actionsChannel = MutableSharedFlow<Action>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )

    protected val _sideEffects = Channel<SideEffect>(
        capacity = Int.MAX_VALUE,
    )

    protected fun getState(): ViewState {
        return _viewState.value
    }

    protected fun setState(reducer: ViewState.() -> ViewState) {
        _viewState.update(reducer)
    }

    protected abstract suspend fun reduce(action: Action)

    val sideEffects = _sideEffects.receiveAsFlow()

    init {
        launchMain {
            actionsChannel.collect { reduce(it) }
        }
    }

    protected fun launchMain(
        block: suspend CoroutineScope.() -> Unit,
    ): Job = viewModelScope.launch(
        context = dispatcherProvider.main(),
        block = block,
    )

    /**
     * Launches a new coroutine in [viewModelScope] on [Dispatcher.io][DispatcherProvider.io].
     */
    protected fun launchIo(
        block: suspend CoroutineScope.() -> Unit,
    ): Job = viewModelScope.launch(
        context = dispatcherProvider.io(),
        block = block,
    )

    protected open fun <T : Any?> (suspend () -> T).execute(
        retainValue: KProperty1<ViewState, Async<T>>? = null,
        reducer: suspend (Async<T>) -> Unit,
    ): Job {
        return launchIo {
            runCatching {
                reducer(Loading(value = retainValue?.get(getState())?.invoke()))
                val result = invoke()
                reducer(Success(result))
                result
            }.onFailure { ex ->
                Fail(ex)
            }
        }
    }

    protected open fun <T : Any?, U : Any?> (suspend () -> T).subscribe(
        flow: suspend (T) -> Flow<U>,
        reducer: suspend (Async<U>) -> Unit,
    ): Job {
        return launchIo {
            runCatching {
                reducer(Loading(value = null))
                val result = invoke()
                flow(result).catch { ex -> Fail(ex) }.collect { reducer(Success(it)) }
            }.onFailure { ex ->
                Fail(ex)
            }
        }
    }

    protected open fun <T> Flow<T>.subscribe(
        reducer: suspend (Async<T>) -> Unit,
    ): Job {
        return launchIo {
            reducer(Loading(value = null))
            catch { ex ->  Fail(ex) }
                .collect { reducer(Success(it)) }
        }
    }
}