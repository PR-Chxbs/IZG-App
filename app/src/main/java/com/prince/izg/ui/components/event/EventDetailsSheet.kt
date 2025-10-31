package com.prince.izg.ui.components.event

import androidx.compose.material3.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.prince.izg.data.remote.dto.Event.EventResponse
import kotlinx.coroutines.coroutineScope
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.launch
import androidx.compose.runtime.rememberCoroutineScope
import com.prince.izg.ui.endpoints.admin.viewmodel.event.EventViewModel


@Composable
fun EventDetailsSheet(
    event: EventResponse,
    viewModel: EventViewModel,
    token: String,
    onClose: () -> Unit,
    onNavigate: (Int) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val formattedDate = remember(event.event_date) {
        try {
            val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
            val outputFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")
            val parsed = LocalDateTime.parse(event.event_date, inputFormatter)
            parsed.format(outputFormatter)
        } catch (e: Exception) {
            event.event_date
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        if (!event.image_url.isNullOrEmpty()) {
            Image(
                painter = rememberAsyncImagePainter(event.image_url),
                contentDescription = event.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            text = event.name,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text("$formattedDate • ${event.start_time} - ${event.end_time}")
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Place,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(event.location)
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = event.description,
            style = MaterialTheme.typography.bodyLarge,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Created on ${event.created_at.take(10)}",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                onClick = {
                    coroutineScope.launch {
                        viewModel.deleteEvent(token, event.id)
                        onClose()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
            ) {
                Text("Delete", color = Color.White)
            }

            Button(
                onClick = {
                    onClose()
                    onNavigate(event.id)
                }
            ) {
                Text("Edit")
            }
        }
    }
}
