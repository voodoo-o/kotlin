package com.example.alistwithdetails.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alistwithdetails.data.model.Repo
import com.example.alistwithdetails.domain.usecase.GetFavoriteReposUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoriteReposUseCase: GetFavoriteReposUseCase
) : ViewModel() {

    val favoriteRepos: StateFlow<List<Repo>> = getFavoriteReposUseCase.execute()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
}