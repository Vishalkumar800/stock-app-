package com.rach.stockapp.presentations.navigation

import org.intellij.lang.annotations.Pattern

sealed class Screens(val route: String) {
    object HomeScreen : Screens("HomeScreen")
    object TopGainerAndLosers : Screens("TopGainersAndLosers/{type}"){
        fun createRoute(type: String) = "TopGainersAndLosers/$type"
    }
    object StockDetailsScreen : Screens("StockDetailsScreen/{symbol}"){
        fun createRoute(symbol: String) = "StockDetailsScreen/$symbol"
    }

    object WatchListScreen : Screens("WatchListScreen")

    object WatchListDetailsScreen: Screens("WatchListDetailsScreen/{title}/{id}"){
        fun createRoute(title: String,id: Int) = "WatchListDetailsScreen/$title/$id"
    }

    object SearchScreen: Screens("SearchScreen")
}