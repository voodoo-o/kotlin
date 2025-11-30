package com.example.alistwithdetails.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.RepoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RepoDetailsUiState(
    val repo: Repo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class RepoDetailsViewModel(
    private val repoId: Long,
    private val repoRepository: RepoRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepoDetailsUiState(isLoading = true))
    val uiState: StateFlow<RepoDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRepoDetails()
    }

    private fun loadRepoDetails() {
        val repo = repoRepository.getRepoById(repoId)
        if (repo != null) {
            _uiState.value = RepoDetailsUiState(repo = repo)
        } else {
            _uiState.value = RepoDetailsUiState(error = "Repository not found")
        }
    }
}

class RepoDetailsViewModelFactory(
    private val repoId: Long,
    private val repoRepository: RepoRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepoDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepoDetailsViewModel(repoId, repoRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}