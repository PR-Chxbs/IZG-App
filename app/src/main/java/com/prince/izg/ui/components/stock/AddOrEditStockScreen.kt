package com.prince.izg.ui.components.stock

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prince.izg.ui.endpoints.admin.viewmodel.stock.StockViewModel
import com.prince.izg.data.remote.dto.Stock.StockRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditStockScreen(
    stockId: Int,
    token: String,
    stockViewModel: StockViewModel,
    navController: NavController,
    onBack: () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val uiState by stockViewModel.uiState.collectAsState()
    val stockItem = uiState.stockItems.find { it.id == stockId }

    var name by remember { mutableStateOf(TextFieldValue(stockItem?.name ?: "")) }
    var quantity by remember { mutableStateOf((stockItem?.quantity ?: 0).toString()) }
    var categoryId by remember { mutableStateOf((stockItem?.category_id ?: 1).toString()) }
    var imageUrl by remember { mutableStateOf(TextFieldValue(stockItem?.image ?: "")) }

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopAppBar(
                title = {
                    Text(if (stockId == -1) "Add Stock" else "Edit Stock")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it },
                label = { Text("Quantity") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = categoryId,
                onValueChange = { categoryId = it },
                label = { Text("Category ID") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = imageUrl,
                onValueChange = { imageUrl = it },
                label = { Text("Image URL (optional)") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val request = StockRequest(
                        name = name.text ?: "Name",
                        quantity = quantity.toIntOrNull() ?: 0,
                        category_id = categoryId.toIntOrNull() ?: 1,
                        image = imageUrl.text.ifBlank { "image" }
                    )

                    if (stockId == -1) {
                        stockViewModel.addStock(token, request)
                    } else {
                        stockViewModel.updateStock(token, stockId, request)
                    }

                    onBack()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (stockId == -1) "Create" else "Update")
            }
        }
    }
}
