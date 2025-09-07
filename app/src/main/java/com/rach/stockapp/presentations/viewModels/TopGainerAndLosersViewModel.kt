package com.rach.stockapp.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rach.stockapp.domain.model.ExploreResponse
import com.rach.stockapp.domain.repository.Gainers
import com.rach.stockapp.utils.Resources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopGainerAndLosersViewModel @Inject constructor(
    private val repository : Gainers
): ViewModel() {

    private val _gainerState = MutableStateFlow<Resources<ExploreResponse>>(Resources.Loading())
    val gainerState : StateFlow<Resources<ExploreResponse>> = _gainerState.asStateFlow()

    init {
        fetchTopGainerAndLosers()
    }

    fun fetchTopGainerAndLosers(){
        viewModelScope.launch {
            repository.gainers().collect { data ->
                _gainerState.value = data
            }
        }
    }

}