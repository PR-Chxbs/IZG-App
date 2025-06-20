package com.prince.izg.ui.endpoints.admin.viewmodel.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prince.izg.data.repository.PostRepository
import com.prince.izg.data.repository.UserRepository

class PostViewModelFactory(
    private val postRepository: PostRepository,
    private val userRepository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PostViewModel::class.java)) {
            return PostViewModel(postRepository, userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
