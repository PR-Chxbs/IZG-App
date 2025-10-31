package com.prince.izg.ui.endpoints.admin.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.prince.izg.R
import com.prince.izg.data.remote.dto.Post.PostResponse
import com.prince.izg.ui.components.shared.BottomNavBar
import com.prince.izg.ui.components.shared.BottomNavItem
import com.prince.izg.ui.components.shared.TempConfirmDeleteDialog
import com.prince.izg.ui.endpoints.admin.viewmodel.post.PostViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostScreen(
    viewModel: PostViewModel,
    token: String,
    navController: NavController,
    bottomBar: @Composable () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value

    // Fetch posts if empty
    LaunchedEffect(Unit) {
        if (uiState.posts.isEmpty()) {
            viewModel.getPosts(token)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Post") })
        },
        bottomBar = bottomBar,
        floatingActionButton = {
            FloatingActionButton(onClick = {
                Log.d("PostScreen", "(PostScreen) Clicked FAB")
                navController.navigate("createPost/-1")
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Article")
            }
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.posts) { post ->
                    ArticleCard(
                        post = post,
                        onDelete = { postId -> viewModel.deletePost(token, postId) },
                        onEdit = { postId -> navController.navigate("createPost/$postId") },
                        navController = navController,
                        token = token
                    )
                }
            }
        }
    }
}

@Composable
fun ArticleCard(
    post: PostResponse,
    onDelete: (Int) -> Unit,
    onEdit: (Int) -> Unit, // for future use
    navController: NavController,
    token: String
) {
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        TempConfirmDeleteDialog (
            title = "Delete Article",
            message = "Are you sure you want to delete '${post.title}'? This action cannot be undone.",
            onConfirm = {
                onDelete(post.id)
                showDialog = false
            },
            onDismiss = { showDialog = false }
        )
    }

    val authorName = post.first_name ?: "Anonymous"

    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable {  }
            .shadow(2.dp),
        onClick = {
            navController.navigate("readArticle/${post.id}")
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
        ) {
            var postCoverImage: String

            if (post.cover_image.isNullOrEmpty()) {
                postCoverImage = "https://plus.unsplash.com/premium_photo-1684581214880-2043e5bc8b8b?ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8NXx8YmxvZyUyMGNvdmVyfGVufDB8fDB8fHww&fm=jpg&q=60&w=3000"
            } else {
                postCoverImage = post.cover_image
            }

            Image(
                painter = rememberAsyncImagePainter(postCoverImage), // use your placeholder image here
                contentDescription = "Article Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "by $authorName, ${formatDate(post.updated_at)}",
                color = Color(0xFFB7B7B7),
                fontSize = 12.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = post.title,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = post.content.take(100) + "...",
                color = Color(0xFFB7B7B7),
                fontSize = 13.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(onClick = { onEdit(post.id) }) {
                    Text("Edit")
                }
                TextButton(onClick =  { showDialog = true }) {
                    Text("Delete")
                }
            }
        }
    }
}

fun formatDate(isoDate: String): String {
    // Optionally format ISO string into something like "April 26 2025"
    return isoDate.take(10) // Temporarily just return YYYY-MM-DD
}
