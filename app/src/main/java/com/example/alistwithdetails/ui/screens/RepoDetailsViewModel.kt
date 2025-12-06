package com.example.alistwithdetails.ui.screens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.domain.usecase.AddToFavoritesUseCase
import com.example.alistwithdetails.domain.usecase.GetRepoDetailsUseCase
import com.example.alistwithdetails.domain.usecase.IsFavoriteUseCase
import com.example.alistwithdetails.domain.usecase.RemoveFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RepoDetailsUiState(
    val repo: Repo? = null,
    val isLoading: Boolean = false,
    val error: String? = null,
    val isFavorite: Boolean = false
)

@HiltViewModel
class RepoDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRepoDetailsUseCase: GetRepoDetailsUseCase,
    private val isFavoriteUseCase: IsFavoriteUseCase,
    private val addToFavoritesUseCase: AddToFavoritesUseCase,
    private val removeFromFavoritesUseCase: RemoveFromFavoritesUseCase
) : ViewModel() {

    private val owner: String = savedStateHandle.get<String>("owner")!!
    private val repoName: String = savedStateHandle.get<String>("repoName")!!

    private val _uiState = MutableStateFlow(RepoDetailsUiState())
    val uiState: StateFlow<RepoDetailsUiState> = _uiState.asStateFlow()

    init {
        loadRepoDetails()
        checkIfFavorite()
    }

    private fun loadRepoDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            getRepoDetailsUseCase.execute(owner, repoName)
                .onSuccess { repo ->
                    _uiState.update { it.copy(isLoading = false, repo = repo) }
                }
                .onFailure { error ->
                    _uiState.update { it.copy(isLoading = false, error = error.localizedMessage) }
                }
        }
    }

    private fun checkIfFavorite() {
        viewModelScope.launch {
            // We need to wait for repo details to be loaded first
            uiState.first { it.repo != null }
            uiState.value.repo?.let {
                isFavoriteUseCase.execute(it.id).collectLatest {
                    isFavorite -> _uiState.update { it.copy(isFavorite = isFavorite) }
                }
            }
        }
    }

    fun toggleFavorite() {
        viewModelScope.launch {
            val repo = _uiState.value.repo ?: return@launch
            if (_uiState.value.isFavorite) {
                removeFromFavoritesUseCase.execute(repo)
            } else {
                addToFavoritesUseCase.execute(repo)
            }
        }
    }
}