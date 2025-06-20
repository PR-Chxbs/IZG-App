package com.prince.izg.ui.components.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.prince.izg.R
import com.prince.izg.ui.components.shared.BottomNavBar
import com.prince.izg.ui.endpoints.admin.viewmodel.post.PostViewModel
import com.prince.izg.utils.formatDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReadArticleScreen(
    navController: NavController,
    postId: Int,
    viewModel: PostViewModel,
    token: String,
    bottomBar: @Composable () -> Unit
) {
    val uiState = viewModel.uiState.collectAsState().value
    val post = uiState.selectedPost

    // Trigger loading if not already selected
    LaunchedEffect(postId) {
        viewModel.getPostById(token, postId)
    }

    Scaffold(
        bottomBar = bottomBar,
        topBar = {
            TopAppBar(
                title = { Text("Read Article") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.clearSelectedPost()
                        navController.popBackStack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (uiState.isLoading || post == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = painterResource(id = R.drawable.article_placeholder_image),
                    contentDescription = "Article Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                    Text(
                        text = post.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = MaterialTheme.typography.headlineSmall.fontSize
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "by Author ${post.author_id}, ${formatDate(post.updated_at)}",
                        color = Color(0xFFB7B7B7),
                        style = MaterialTheme.typography.bodySmall
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = post.content,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.DarkGray
                    )
                }

                Spacer(modifier = Modifier.height(50.dp))
            }
        }
    }
}
