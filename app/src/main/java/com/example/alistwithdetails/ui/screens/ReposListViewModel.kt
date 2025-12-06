package com.example.alistwithdetails.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.preferences.FilterRepository
import com.example.alistwithdetails.domain.state.FilterStateCache
import com.example.alistwithdetails.domain.usecase.GetReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ReposListUiState(
    val isLoading: Boolean = true, // Start in loading state
    val repos: List<Repo> = emptyList(),
    val error: String? = null
)

@HiltViewModel
class ReposListViewModel @Inject constructor(
    private val getReposUseCase: GetReposUseCase,
    private val filterRepository: FilterRepository,
    private val filterStateCache: FilterStateCache
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReposListUiState())
    val uiState: StateFlow<ReposListUiState> = _uiState.asStateFlow()

    val isFilterActive: StateFlow<Boolean> = filterStateCache.isFilterActive

    private var originalRepos: List<Repo> = emptyList()
    private var lastSearchedUser: String = ""
    private val defaultUser = "JakeWharton" // Default user to show on start

    init {
        viewModelScope.launch {
            val language = filterRepository.languageFilter.first()
            filterStateCache.setFilterActive(language.isNotBlank())
            // Fetch default user's repos on start
            onSearch(defaultUser)
        }
    }

    fun onSearch(user: String) {
        if (user.isBlank()) {
            return
        }
        lastSearchedUser = user
        fetchRepos(user)
    }

    fun retry() {
        fetchRepos(lastSearchedUser)
    }

    private fun fetchRepos(user: String) {
        viewModelScope.launch {
            _uiState.value = ReposListUiState(isLoading = true)
            getReposUseCase.execute(user)
                .onSuccess { repos ->
                    originalRepos = repos
                    applyFilter()
                }
                .onFailure { throwable ->
                    _uiState.value = ReposListUiState(isLoading = false, error = throwable.message ?: "An unknown error occurred")
                }
        }
    }

    private fun applyFilter() {
        viewModelScope.launch {
            val language = filterRepository.languageFilter.first()
            val filteredList = if (language.isBlank()) {
                originalRepos
            } else {
                originalRepos.filter { it.language?.equals(language, ignoreCase = true) == true }
            }
            _uiState.value = ReposListUiState(isLoading = false, repos = filteredList)
        }
    }
}
