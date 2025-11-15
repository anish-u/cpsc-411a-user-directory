package com.uanish.userdirectory

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.uanish.userdirectory.presentation.UserListScreen
import com.uanish.userdirectory.presentation.UserListViewModel
import com.uanish.userdirectory.presentation.UserListViewModelFactory
import com.uanish.userdirectory.ui.theme.UserDirectoryTheme

class MainActivity : ComponentActivity() {

    private val viewModel: UserListViewModel by viewModels {
        val app = application as UserDirectoryApp
        UserListViewModelFactory(app.userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UserDirectoryTheme {
                UserListScreen(viewModel = viewModel)
            }
        }
    }
}
