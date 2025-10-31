package com.prince.izg.ui.endpoints.admin.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar
import com.prince.izg.data.remote.dto.Event.EventRequest
import com.prince.izg.data.remote.dto.Event.EventResponse
import com.prince.izg.ui.endpoints.admin.viewmodel.event.EventViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventFormScreen(
    viewModel: EventViewModel,
    token: String,
    eventId: Int,
    onFinish: () -> Unit
) {


    val uiState by viewModel.uiState.collectAsState()
    var event: EventResponse? = EventResponse(0, 0, "", "", "", "", "", "", "","");

    val isEdit = eventId == -1

    if (isEdit) {
        viewModel.getEventById(token, eventId)
        event = uiState.selectedEvent
    }

    var name by remember { mutableStateOf(event?.name ?: "") }
    var description by remember { mutableStateOf(event?.description ?: "") }
    var location by remember { mutableStateOf(event?.location ?: "") }
    var date by remember { mutableStateOf(event?.event_date ?: "") }
    var startTime by remember { mutableStateOf(event?.start_time ?: "") }
    var endTime by remember { mutableStateOf(event?.end_time ?: "") }
    var imageUrl by remember { mutableStateOf(event?.image_url ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEdit) "Edit Event" else "Create Event") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Event Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("Description") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date (YYYY-MM-DD)") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = startTime,
                    onValueChange = { startTime = it },
                    label = { Text("Start Time (HH:MM)") },
                    modifier = Modifier.weight(1f)
                )

                OutlinedTextField(
                    value = endTime,
                    onValueChange = { endTime = it },
                    label = { Text("End Time (HH:MM)") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Image URL") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val eventRequest = EventRequest(
                        name = name,
                        description = description,
                        location = location,
                        event_date = date,
                        start_time = startTime,
                        end_time = endTime,
                        image_url = if (imageUrl.isBlank()) null else imageUrl
                    )

                    if (isEdit) {
                        viewModel.updateEvent(token, event!!.id, eventRequest)
                    } else {
                        viewModel.addEvent(token, eventRequest)
                    }

                    onFinish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (isEdit) "Update Event" else "Create Event")
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            }

            uiState.error?.let { Text(it, color = Color.Red) }
        }
    }
}
