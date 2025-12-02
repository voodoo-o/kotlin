package com.example.alistwithdetails.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.data.repository.NetworkRepoRepository
import com.example.alistwithdetails.domain.usecase.GetReposUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ReposListUiState(
    val repos: List<Repo> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

class ReposListViewModel(private val getReposUseCase: GetReposUseCase) : ViewModel() {

    private val _uiState = MutableStateFlow(ReposListUiState())
    val uiState: StateFlow<ReposListUiState> = _uiState.asStateFlow()

    fun loadRepos(user: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            getReposUseCase.execute(user)
                .onSuccess {
                    repos -> _uiState.update { it.copy(isLoading = false, repos = repos) }
                }
                .onFailure {
                    error -> _uiState.update { it.copy(isLoading = false, error = error.localizedMessage) }
                }
        }
    }
}


class ReposListViewModelFactory : ViewModelProvider.Factory {
    private val repoRepository by lazy { NetworkRepoRepository() }
    private val getReposUseCase by lazy { GetReposUseCase(repoRepository) }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ReposListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ReposListViewModel(getReposUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}