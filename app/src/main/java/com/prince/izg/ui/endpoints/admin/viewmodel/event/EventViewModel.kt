package com.prince.izg.ui.endpoints.admin.viewmodel.event

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prince.izg.data.remote.dto.Event.EventRequest
import com.prince.izg.data.remote.dto.Event.EventResponse
import com.prince.izg.data.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class EventUiState(
    val events: List<EventResponse> = emptyList(),
    val selectedEvent: EventResponse? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class EventViewModel(
    private val eventRepository: EventRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(EventUiState())
    val uiState: StateFlow<EventUiState> = _uiState

    fun getEvents(token: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = eventRepository.getEvents()
                if (response.isSuccessful) {
                    _uiState.update { it.copy(events = response.body() ?: emptyList(), isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun getEventById(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = eventRepository.getEventById(id)
                if (response.isSuccessful) {
                    _uiState.update { it.copy(selectedEvent = response.body(), isLoading = false) }
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun addEvent(token: String, event: EventRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = eventRepository.addEvent(token, event)
                if (response.isSuccessful) {
                    getEvents(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun updateEvent(token: String, id: Int, event: EventRequest) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = eventRepository.updateEvent(token, id, event)
                if (response.isSuccessful) {
                    getEvents(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun deleteEvent(token: String, id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                val response = eventRepository.deleteEvent(token, id)
                if (response.isSuccessful) {
                    getEvents(token)
                } else {
                    _uiState.update { it.copy(error = response.message(), isLoading = false) }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(error = e.message, isLoading = false) }
            }
        }
    }

    fun clearSelectedEvent() {
        _uiState.update { it.copy(selectedEvent = null) }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}
