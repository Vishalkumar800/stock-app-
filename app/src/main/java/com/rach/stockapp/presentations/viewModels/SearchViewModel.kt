package com.rach.stockapp.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rach.stockapp.domain.model.search.SearchResultModel
import com.rach.stockapp.domain.repository.SearchRepository
import com.rach.stockapp.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: SearchRepository
) : ViewModel() {

    private val _searchResult = MutableStateFlow<Resources<SearchResultModel>>(Resources.Success(SearchResultModel()))
    val searchResult: StateFlow<Resources<SearchResultModel>> = _searchResult.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private var searchJob: Job? = null

    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery

        searchJob?.cancel()
        searchJob = viewModelScope.launch {
            delay(500)
            if (newQuery.isNotBlank()) {
                search(newQuery)
            } else {
                _searchResult.value = Resources.Success(SearchResultModel())
            }
        }
    }

    fun clearSearch() {
        _searchQuery.value = ""
        _searchResult.value = Resources.Success(SearchResultModel())
    }

    fun search(searchKeyword: String) {
        viewModelScope.launch {
            _searchResult.value = Resources.Loading()
            repository.searchResult(searchKeyword).collect { data ->
                _searchResult.value = data
            }
        }
    }

}