package com.example.alistwithdetails.di

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FilterStateHolder @Inject constructor() {
    private val _isFilterApplied = MutableStateFlow(false)
    val isFilterApplied = _isFilterApplied.asStateFlow()

    fun setFilterApplied(isApplied: Boolean) {
        _isFilterApplied.value = isApplied
    }
}