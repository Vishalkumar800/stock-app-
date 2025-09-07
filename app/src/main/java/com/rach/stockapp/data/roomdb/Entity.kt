package com.rach.stockapp.data.roomdb

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("watchedList")
data class WatchList(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
)

@Entity(
    tableName = "savedStockInfo",
    foreignKeys = [
        ForeignKey(
            entity = WatchList::class,
            parentColumns = ["id"],
            childColumns = ["watchListId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class SavedStockInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val stockName: String,
    val stockPrice : String,
    val symbol : String,
    val watchListId: Int
)