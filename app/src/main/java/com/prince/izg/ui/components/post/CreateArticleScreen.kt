package com.prince.izg.ui.components.post

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prince.izg.data.remote.dto.Post.PostRequest
import com.prince.izg.ui.endpoints.admin.viewmodel.post.PostViewModel
import android.util.Base64
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateArticleScreen(
    postId: Int, // -1 means "new post"
    navController: NavController,
    token: String,
    viewModel: PostViewModel,
    bottomBar: @Composable () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var slug by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    val uiState = viewModel.uiState.collectAsState().value

    val selectedPost = uiState.selectedPost

    val isPostCreated by viewModel.isPostCreated.collectAsState()
    Log.d("PostScreen", "(CreateArticleScreen) Entered create article screen")

    LaunchedEffect(selectedPost) {
        selectedPost?.let { post ->
            title = post.title ?: ""
            slug = post.slug ?: ""
            content = post.content ?: ""
        }
    }

    // Fetch user if editing
    LaunchedEffect(postId) {
        if (postId != -1) {
            viewModel.getPostById(token, postId)
        } else {
            title = ""
            slug = ""
            content = ""
        }
    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = bottomBar,
        topBar = {
            TopAppBar(
                title = {
                    Text(if (postId == -1) "Create article" else "Edit article")
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(16.dp)
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            /*
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Image,
                    contentDescription = "Upload Image",
                    tint = Color.DarkGray,
                    modifier = Modifier.size(48.dp)
                )
            }*/

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = slug,
                onValueChange = { slug = it },
                label = { Text("Slug") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = content,
                onValueChange = { content = it },
                label = { Text("Content") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    val post = PostRequest(
                        author_id = 13, // Replace with actual admin ID when available
                        title = title,
                        slug = slug,
                        content = content,
                        cover_image = null,
                        published = false,
                        published_at = null
                    )
                    if (postId == -1) {
                        viewModel.addPost(token, post)
                    } else {
                        viewModel.updatePost(token, postId, post = post)
                    }

                }) {
                    Text("Save")
                }

                Button(onClick = {
                    val post = PostRequest(
                        author_id = 13,
                        title = title,
                        slug = slug,
                        content = content,
                        cover_image = null,
                        published = true,
                        published_at = null // Set properly on backend
                    )
                    if (postId == -1) {
                        viewModel.addPost(token, post)
                    } else {
                        viewModel.publishPost(token, postId)
                        viewModel.updatePost(token, postId, post = post)
                    }
                }) {
                    Text("Save & Publish")
                }
            }
        }
    }

    LaunchedEffect(uiState.error) {
        uiState.error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    LaunchedEffect(isPostCreated) {
        if (isPostCreated) {
            navController.popBackStack()
            viewModel.resetPostCreatedFlag()
        }
    }
}

fun slugify(input: String): String {
    return input
        .trim()                      // Remove leading/trailing spaces
        .lowercase()                 // Convert to lowercase
        .replace(Regex("\\s+"), "-") // Replace 1+ spaces with a single "-"
}

fun getUserIdFromJwt(token: String): Int? {
    return try {
        // JWT format: header.payload.signature
        val parts = token.split(".")
        if (parts.size < 2) return null

        // Decode the payload (second part)
        val payloadJson = String(Base64.decode(parts[1], Base64.URL_SAFE or Base64.NO_PADDING or Base64.NO_WRAP))

        // Convert to JSONObject and extract "user_id"
        val jsonObject = JSONObject(payloadJson)
        jsonObject.optInt("user_id") // returns 0 if missing
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}