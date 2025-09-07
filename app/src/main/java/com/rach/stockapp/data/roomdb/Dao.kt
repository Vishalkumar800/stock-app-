package com.rach.stockapp.data.roomdb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface WatchListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWatchList(watchList: WatchList)

    @Query("SELECT * FROM watchedList")
    fun getAllWatchList(): Flow<List<WatchList>>

}

@Dao
interface SavedStockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStock(savedStockInfoEntity: SavedStockInfoEntity)

    @Query("SELECT * FROM savedStockInfo WHERE watchListId = :watchListId")
    fun getAllOfflineStock(watchListId: Int): Flow<List<SavedStockInfoEntity>>

}