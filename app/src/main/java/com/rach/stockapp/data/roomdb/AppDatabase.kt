package com.rach.stockapp.data.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [WatchList::class , SavedStockInfoEntity::class],
    exportSchema = false,
    version = 1
)
abstract class AppDatabase : RoomDatabase(){
    abstract fun watchListDao(): WatchListDao
    abstract fun stockInfoDao(): SavedStockDao
}