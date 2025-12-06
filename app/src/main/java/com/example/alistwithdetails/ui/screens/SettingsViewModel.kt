package com.example.alistwithdetails.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alistwithdetails.data.preferences.FilterRepository
import com.example.alistwithdetails.domain.state.FilterStateCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val filterRepository: FilterRepository,
    private val filterStateCache: FilterStateCache
) : ViewModel() {

    // Read the current filter from DataStore and expose it to the UI
    val languageFilter = filterRepository.languageFilter
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), "")

    fun onSaveClick(language: String) {
        viewModelScope.launch {
            filterRepository.saveLanguageFilter(language)
            // Update the badge state
            filterStateCache.setFilterActive(language.isNotBlank())
        }
    }
}
