package com.rach.stockapp.data.repositoryImp

import com.rach.stockapp.data.roomdb.SavedStockDao
import com.rach.stockapp.data.roomdb.SavedStockInfoEntity
import com.rach.stockapp.data.roomdb.WatchList
import com.rach.stockapp.data.roomdb.WatchListDao
import com.rach.stockapp.domain.repository.WatchListStockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WatchListStockRepoImp @Inject constructor(
    private val watchListDao: WatchListDao,
    private val savedStockDao: SavedStockDao
) : WatchListStockRepository {
    override suspend fun addWatchList(watchList: WatchList) {
        return watchListDao.addWatchList(watchList)
    }

    override fun getAllWatchList(): Flow<List<WatchList>> {
        return watchListDao.getAllWatchList()
    }

    override suspend fun insertStock(savedStockInfoEntity: SavedStockInfoEntity) {
        return savedStockDao.insertStock(savedStockInfoEntity)
    }

    override fun getAllOfflineStockByWatchList(watchListId: Int): Flow<List<SavedStockInfoEntity>> {
        return savedStockDao.getAllOfflineStock(watchListId)
    }
}