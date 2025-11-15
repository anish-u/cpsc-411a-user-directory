package com.uanish.userdirectory.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uanish.userdirectory.data.UserRepository
import com.uanish.userdirectory.domain.User
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class UserListUiState(
    val users: List<User> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val searchQuery: String = ""
)

@OptIn(FlowPreview::class)
class UserListViewModel(
    private val repository: UserRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val _isRefreshing = MutableStateFlow(false)
    private val _errorMessage = MutableStateFlow<String?>(null)

    private val usersFlow: Flow<List<User>> =
        _searchQuery
            .debounce(300) // small delay to avoid querying on every keystroke
            .flatMapLatest { query ->
                repository.getUsers(query.ifBlank { null })
            }

    val uiState: StateFlow<UserListUiState> =
        combine(usersFlow, _isRefreshing, _errorMessage, _searchQuery) {
                users, isLoading, error, query ->
            UserListUiState(
                users = users,
                isLoading = isLoading,
                errorMessage = error,
                searchQuery = query
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = UserListUiState()
        )

    init {
        // offline-first: show DB immediately, then refresh from API
        refreshUsers()
    }

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery
    }

    fun refreshUsers() {
        viewModelScope.launch {
            _isRefreshing.value = true
            _errorMessage.value = null
            val result = repository.refreshUsers()
            if (result.isFailure) {
                _errorMessage.value = "Could not refresh users. Showing cached data."
            }
            _isRefreshing.value = false
        }
    }
}
