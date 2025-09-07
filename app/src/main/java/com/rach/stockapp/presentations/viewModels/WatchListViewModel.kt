package com.rach.stockapp.presentations.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rach.stockapp.data.roomdb.SavedStockInfoEntity
import com.rach.stockapp.data.roomdb.WatchList
import com.rach.stockapp.domain.repository.WatchListStockRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UiEvent {
    data class ShowToast(val message: String) : UiEvent()
}

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val watchListStockRepository: WatchListStockRepository
) : ViewModel() {
    private val _watchList = MutableStateFlow<List<WatchList>>(emptyList())
    val watchList: StateFlow<List<WatchList>> = _watchList.asStateFlow()

    private val _stock = MutableStateFlow<List<SavedStockInfoEntity>>(emptyList())
    val stock: StateFlow<List<SavedStockInfoEntity>> = _stock.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        fetchWatchList()
    }

    fun fetchWatchList() {
        viewModelScope.launch {
            watchListStockRepository.getAllWatchList().collect { watchListData ->
                _watchList.value = watchListData
            }
        }
    }

    fun addWatchList(name: String) {
        viewModelScope.launch {
            watchListStockRepository.addWatchList(
                watchList = WatchList(
                    title = name
                )
            )
            _uiEvent.emit(UiEvent.ShowToast("New Watch List Added"))
        }
    }

    fun savedStock(savedStockInfoEntity: SavedStockInfoEntity) {
        viewModelScope.launch {
            watchListStockRepository.insertStock(
                savedStockInfoEntity = savedStockInfoEntity
            )
            _uiEvent.emit(UiEvent.ShowToast("${savedStockInfoEntity.stockName} Added Successfully"))
        }
    }

    fun getAllSavedStockByWatchListId(watchListId: Int) {
        viewModelScope.launch {
            watchListStockRepository.getAllOfflineStockByWatchList(watchListId).collect { data ->
                _stock.value = data
            }
        }
    }
}