package com.uanish.userdirectory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.uanish.userdirectory.data.UserRepository

class UserListViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
