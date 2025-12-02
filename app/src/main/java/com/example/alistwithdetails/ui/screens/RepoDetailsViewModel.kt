package com.example.alistwithdetails.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.NetworkRepoRepository
import com.example.alistwithdetails.domain.usecase.GetRepoDetailsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class RepoDetailsUiState(
    val repo: Repo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class RepoDetailsViewModel(
    private val owner: String,
    private val repoName: String,
    private val getRepoDetailsUseCase: GetRepoDetailsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(RepoDetailsUiState())
    val uiState: StateFlow<RepoDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRepoDetails()
    }

    fun loadRepoDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getRepoDetailsUseCase.execute(owner, repoName)
                .onSuccess { repo ->
                    _uiState.update { it.copy(isLoading = false, repo = repo) }
                }
                .onFailure {
                    error -> _uiState.update { it.copy(isLoading = false, error = error.localizedMessage) }
                }
        }
    }
}

class RepoDetailsViewModelFactory(
    private val owner: String,
    private val repoName: String
) : ViewModelProvider.Factory {

    private val repoRepository by lazy { NetworkRepoRepository() }
    private val getRepoDetailsUseCase by lazy { GetRepoDetailsUseCase(repoRepository) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepoDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepoDetailsViewModel(owner, repoName, getRepoDetailsUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}