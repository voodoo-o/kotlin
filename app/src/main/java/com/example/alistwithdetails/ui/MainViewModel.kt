package com.example.alistwithdetails.ui

import androidx.lifecycle.ViewModel
import com.example.alistwithdetails.domain.state.FilterStateCache
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    filterStateCache: FilterStateCache
) : ViewModel() {

    // Expose the filter state directly from the cache
    val isFilterApplied = filterStateCache.isFilterActive
}
