package com.prince.izg.ui.endpoints.admin.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.prince.izg.ui.components.shared.BottomNavBar
import com.prince.izg.ui.components.shared.BottomNavItem
import com.prince.izg.ui.endpoints.admin.viewmodel.stock.StockViewModel
import com.prince.izg.data.remote.dto.Stock.StockRequest
import com.prince.izg.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockScreen(
    viewModel: StockViewModel,
    token: String,
    navToAddOrEditStock: (stockId: Int?) -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var pendingUpdate by remember { mutableStateOf<Pair<Int, Int>?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAllStock(token)
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Stock") })
        },
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(onClick = { navToAddOrEditStock(-1) }) {
                Icon(Icons.Default.Add, contentDescription = "Add Article")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.error != null) {
                LaunchedEffect(uiState.error) {
                    snackbarHostState.showSnackbar(uiState.error ?: "Unknown error")
                    viewModel.clearError()
                }
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize().padding(8.dp)) {
                    items(uiState.stockItems) { item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                item.image?.let { imageUrl ->
                                    Image(
                                        painter = painterResource(id = R.drawable.article_placeholder_image),
                                        contentDescription = item.name,
                                        modifier = Modifier
                                            .size(60.dp)
                                            .padding(end = 8.dp)
                                    )
                                }
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(item.name, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                                    Text("Qty: ${item.quantity}", fontSize = 14.sp)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    IconButton(onClick = {
                                        pendingUpdate = item.id to (item.quantity + 1)
                                        showConfirmDialog = true
                                    }) {
                                        Icon(Icons.Default.Add, contentDescription = "Increase")
                                    }
                                    IconButton(onClick = {
                                        if (item.quantity > 0) {
                                            pendingUpdate = item.id to (item.quantity - 1)
                                            showConfirmDialog = true
                                        }
                                    }) {
                                        Icon(Icons.Default.Remove, contentDescription = "Decrease")
                                    }
                                    IconButton(onClick = {
                                        navToAddOrEditStock(item.id)
                                    }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit")
                                    }
                                    IconButton(onClick = {
                                        viewModel.deleteStock(token, item.id)
                                    }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showConfirmDialog && pendingUpdate != null) {
        val (id, newQty) = pendingUpdate!!
        AlertDialog(
            onDismissRequest = {
                showConfirmDialog = false
                pendingUpdate = null
            },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.updateStock(
                        token,
                        id,
                        StockRequest(
                            category_id = uiState.stockItems.first { it.id == id }.category_id,
                            name = uiState.stockItems.first { it.id == id }.name,
                            quantity = newQty,
                            image = uiState.stockItems.first { it.id == id }.image
                        )
                    )
                    showConfirmDialog = false
                    pendingUpdate = null
                }) {
                    Text("Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showConfirmDialog = false
                    pendingUpdate = null
                }) {
                    Text("Cancel")
                }
            },
            title = { Text("Confirm Update") },
            text = { Text("Are you sure you want to update the quantity?") }
        )
    }
}
