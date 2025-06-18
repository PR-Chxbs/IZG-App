package com.prince.izg.ui.endpoints.admin.ui

import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.prince.izg.data.remote.dto.User.UserResponse
import com.prince.izg.ui.components.shared.ConfirmDeleteDialog
import com.prince.izg.ui.components.users.UserCard
import com.prince.izg.ui.components.users.UserDetailDialog
import com.prince.izg.ui.endpoints.admin.viewmodel.user.UserViewModel
import androidx.compose.material.icons.filled.PersonAdd

@Composable
fun UsersScreen(
    viewModel: UserViewModel,
    token: String,
    onEditUser: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showUserDetailDialog by remember { mutableStateOf(false) }
    var userToDelete by remember { mutableStateOf<UserResponse?>(null) }

    LaunchedEffect(true) {
        viewModel.getUsers(token)
    }

    Box(modifier = Modifier.fillMaxSize()) {
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

        FloatingActionButton(
            onClick = { onEditUser(-1) }, // -1 will signal "Add New"
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.PersonAdd, contentDescription = "Add User")
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
