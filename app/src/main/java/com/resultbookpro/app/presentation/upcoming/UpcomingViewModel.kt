package com.resultbookpro.app.presentation.upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.resultbookpro.app.data.model.UpcomingEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UpcomingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<UpcomingUiState>(UpcomingUiState.Loading)
    val uiState: StateFlow<UpcomingUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<List<UpcomingEvent>>(emptyList())
    val events: StateFlow<List<UpcomingEvent>> = _events.asStateFlow()

    init {
        loadEvents()
    }

    private fun loadEvents() {
        _uiState.value = UpcomingUiState.Success(_events.value)
    }

    fun addEvent(event: UpcomingEvent) {
        viewModelScope.launch {
            val currentList = _events.value.toMutableList()
            currentList.add(event)
            _events.value = currentList
            _uiState.value = UpcomingUiState.Success(currentList)
        }
    }
}

sealed class UpcomingUiState {
    object Loading : UpcomingUiState()
    data class Success(val events: List<UpcomingEvent>) : UpcomingUiState()
    data class Error(val message: String) : UpcomingUiState()
}
