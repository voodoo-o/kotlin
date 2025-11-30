package com.example.alistwithdetails.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.RepoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class ReposListUiState(
    val repos: List<Repo> = emptyList(),
    val isLoading: Boolean = false
)

class ReposListViewModel(private val repoRepository: RepoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ReposListUiState(isLoading = true))
    val uiState: StateFlow<ReposListUiState> = _uiState.asStateFlow()

    init {
        loadRepos()
    }

    private fun loadRepos() {
        _uiState.value = ReposListUiState(repos = repoRepository.getRepos(), isLoading = false)
    }
}


class ReposListViewModelFactory(private val repoRepository: RepoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReposListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReposListViewModel(repoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
