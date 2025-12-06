package com.example.alistwithdetails.domain.state

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * A singleton cache to hold the state for the filter badge.
 * This allows different ViewModels to communicate whether a filter is active.
 */
@Singleton
class FilterStateCache @Inject constructor() {
    private val _isFilterActive = MutableStateFlow(false)
    val isFilterActive: StateFlow<Boolean> = _isFilterActive

    fun setFilterActive(isActive: Boolean) {
        _isFilterActive.value = isActive
    }
}
