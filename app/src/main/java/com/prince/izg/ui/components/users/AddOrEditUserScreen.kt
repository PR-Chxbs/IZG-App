package com.prince.izg.ui.components.users

import RoleDropdown
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController

import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.text.input.KeyboardType
import com.prince.izg.data.remote.dto.User.UserRequest

import androidx.compose.material3.Scaffold
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import com.prince.izg.ui.components.shared.ShowDatePicker
import com.prince.izg.ui.endpoints.admin.viewmodel.user.UserViewModel
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditUserScreen(
    userId: Int, // -1 means "new user"
    token: String,
    userViewModel: UserViewModel,
    onBack: () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var dob by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }
    var role by remember { mutableStateOf("Admin") }

    var showDatePicker by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val uiState by userViewModel.uiState.collectAsState()
    val isUserCreated by userViewModel.isUserCreated.collectAsState()

    // Fetch user if editing
    LaunchedEffect(userId) {
        if (userId != -1) {
            userViewModel.getUserById(token, userId)
        } else {
            firstName =  ""
            lastName = ""
            username =  ""
            email = ""
            phoneNumber = ""
            dob = ""
            gender = ""
            role = "Admin"
        }
    }

    // Populate fields when user is fetched
    LaunchedEffect(uiState.selectedUser) {
        uiState.selectedUser?.let { user ->
            firstName = user.first_name ?: ""
            lastName = user.second_name ?: ""
            username = user.username ?: ""
            email = user.email ?: ""
            phoneNumber = user.phone_number ?: ""
            dob = user.dob ?: ""
            gender = user.gender ?: ""
            role = user.role ?: "Admin"
        }
    }

    // Fetch user if editing
    LaunchedEffect(userId) {
        if (userId != -1) {
            userViewModel.getUserById(token, userId)
        } else {
            firstName =  ""
            lastName = ""
            username =  ""
            email = ""
            phoneNumber = ""
            dob = ""
            gender = ""
            role = "Admin"
        }
    }

    LaunchedEffect(isUserCreated) {
        if (isUserCreated) {
            Log.d("AddOrEditUserScreen", "User Created")
            onBack()
            userViewModel.resetUserCreatedFlag() // optional helper function to reset
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (userId == -1) "Add User" else "Edit User")
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = bottomBar
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            if (!uiState.error.isNullOrBlank()) {
                Text(
                    text = uiState.error ?: "",
                    color = androidx.compose.ui.graphics.Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text("First Name") },
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text("Last Name") },
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email Address") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = dob,
                onValueChange = {},
                label = { Text("Date of Birth") },
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showDatePicker = true },
                readOnly = true
            )

            OutlinedTextField(
                value = gender,
                onValueChange = { gender = it },
                label = { Text("Gender") },
                modifier = Modifier.fillMaxWidth()
            )

            RoleDropdown(role = role, onRoleChange = { role = it })

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    val request = UserRequest(
                        id = userId,
                        username = username,
                        first_name = firstName,
                        second_name = lastName,
                        gender = gender,
                        dob = "2004-12-23",
                        phone_number = phoneNumber,
                        email = email,
                        role = role
                    )
                    if (userId == -1) {
                        Log.d("AddOrEditUserScreen", "Add button clicked")
                        userViewModel.addUser(token, request)
                    } else {
                        Log.d("AddOrEditUserScreen", "Update button clicked")
                        userViewModel.updateUser(token, userId, request)
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (userId == -1) "Add Member" else "Update Member")
            }
        }

        if (showDatePicker) {
            ShowDatePicker(
                onDateSelected = {
                    dob = it
                    showDatePicker = false
                },
                onDismiss = {
                    showDatePicker = false
                }
            )
        }
    }
}
