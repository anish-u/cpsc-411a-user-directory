package com.uanish.userdirectory.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.uanish.userdirectory.domain.User

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserListScreen(
    viewModel: UserListViewModel
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("User Directory") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            // Search bar
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { viewModel.onSearchQueryChange(it) },
                label = { Text("Search by name or email") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            if (uiState.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }

            uiState.errorMessage?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            if (uiState.users.isEmpty() && !uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No users available")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(uiState.users) { user ->
                        UserItem(user)
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "${user.id}. ${user.name}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "Email: ${user.email}")
            Text(text = "Phone: ${user.phone}")
        }
    }
}
