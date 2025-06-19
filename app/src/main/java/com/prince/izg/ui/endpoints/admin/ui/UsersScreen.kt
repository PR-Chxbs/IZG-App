package com.prince.izg.ui.endpoints.admin.ui

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prince.izg.data.remote.dto.User.UserResponse
import com.prince.izg.ui.components.shared.ConfirmDeleteDialog
import com.prince.izg.ui.components.users.UserCard
import com.prince.izg.ui.components.users.UserDetailDialog
import com.prince.izg.ui.endpoints.admin.viewmodel.user.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsersScreen(
    viewModel: UserViewModel,
    token: String,
    onEditUser: (Int) -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showUserDetailDialog by remember { mutableStateOf(false) }
    var userToDelete by remember { mutableStateOf<UserResponse?>(null) }

    LaunchedEffect(true) {
        viewModel.getUsers(token)
        Log.d("UsersScreen", "Token being passed: $token")
        Log.d("UsersScreen", "(From screen) Fetched users: ${uiState.users}")
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Users") })
        },
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(onClick = { onEditUser(-1) }) {
                Icon(imageVector = Icons.Default.PersonAdd, contentDescription = "Add User")
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    items(uiState.users) { user ->
                        UserCard(
                            user = user,
                            onClick = {
                                viewModel.getUserById(token, user.id)
                                showUserDetailDialog = true
                            },
                            onEdit = { onEditUser(user.id) },
                            onDelete = {
                                userToDelete = user
                                showDeleteDialog = true
                            }
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            if (showUserDetailDialog && uiState.selectedUser != null) {
                UserDetailDialog(
                    user = uiState.selectedUser!!,
                    onDismiss = {
                        showUserDetailDialog = false
                        viewModel.clearSelectedUser()
                    }
                )
            }

            if (showDeleteDialog && userToDelete != null) {
                ConfirmDeleteDialog(
                    user = userToDelete!!,
                    onConfirm = {
                        viewModel.deleteUser(token, userToDelete!!.id)
                        showDeleteDialog = false
                    },
                    onDismiss = { showDeleteDialog = false }
                )
            }
        }
    }
}
