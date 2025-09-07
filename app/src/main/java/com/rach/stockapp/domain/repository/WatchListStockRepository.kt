package com.rach.stockapp.domain.repository

import com.rach.stockapp.data.roomdb.SavedStockInfoEntity
import com.rach.stockapp.data.roomdb.WatchList
import kotlinx.coroutines.flow.Flow

interface WatchListStockRepository {
    suspend fun addWatchList(watchList: WatchList)
    fun getAllWatchList(): Flow<List<WatchList>>

    suspend fun insertStock(savedStockInfoEntity: SavedStockInfoEntity)
    fun getAllOfflineStockByWatchList(watchListId: Int): Flow<List<SavedStockInfoEntity>>
}