package com.cesc.pulltorefresh

import android.util.Log
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

class PtrViewModel : ViewModel() {
    private val channel: Channel<UIEvent> = Channel(Channel.UNLIMITED)
    private val _uiState = MutableStateFlow(UiState.Init)
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()

    init {
        channel.receiveAsFlow()
            .onEach(::handleEvent)
            .launchIn(viewModelScope)
    }

    private fun handleEvent(event: UIEvent) {
        when (event) {
            is UIEvent.FetchAll -> {}
            is UIEvent.Refreshing -> {}
        }
    }

    fun sendEvent(event: UIEvent) {
        channel.trySend(event)
    }

    private fun UiState.reduce(block: UiState.() -> UiState) {
        _uiState.value = _uiState.value.block()
    }
}


@Immutable
data class UiState(
    val isShowFullScreenLoading: Boolean,
    val isPullToRefreshing: Boolean,
    val items: List<ScreenItem>
) {
    companion object {
        val Init = UiState(
            isPullToRefreshing = false,
            isShowFullScreenLoading = true,
            items = emptyList()
        )
    }
}


@Immutable
sealed interface UIEvent {
    object FetchAll : UIEvent
    object Refreshing : UIEvent
}


sealed class ScreenItem {
    object AddNew : ScreenItem()
    data class TodoItem(
        val text: String,
        val isChecked: Boolean
    ) : ScreenItem()
}






