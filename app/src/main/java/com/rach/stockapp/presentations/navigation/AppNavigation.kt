package com.rach.stockapp.presentations.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.rach.stockapp.presentations.ui.ExploreScreen
import com.rach.stockapp.presentations.ui.SearchScreen
import com.rach.stockapp.presentations.ui.Stock
import com.rach.stockapp.presentations.ui.StockDetailsScreen
import com.rach.stockapp.presentations.ui.TopGainersAndLosersUi
import com.rach.stockapp.presentations.ui.WatchListDetailsScreen
import com.rach.stockapp.presentations.ui.WatchListScreens

@Composable
fun AppNavigation(modifier: Modifier = Modifier, navHostController: NavHostController) {

    NavHost(
        navController = navHostController,
        startDestination = Screens.HomeScreen.route
    ){
        composable(
            route = Screens.HomeScreen.route
        ){
            ExploreScreen(
                navController = navHostController
            )
        }

        composable(route = Screens.TopGainerAndLosers.route){backStackEntry ->
            val type = backStackEntry.arguments?.getString("type") ?: "gainers"
            val stocks = navHostController.previousBackStackEntry?.savedStateHandle?.get<List<Stock>>("stocks") ?: emptyList()
            TopGainersAndLosersUi(
                type = type,
                stocks = stocks,
                navController = navHostController
            )
        }

        composable(route = Screens.StockDetailsScreen.route){backStackEntry ->
            val symbol = backStackEntry.arguments?.getString("symbol") ?: ""
            StockDetailsScreen(
                symbol = symbol
            )
        }

        composable(route = Screens.WatchListScreen.route ){
            WatchListScreens(
                navController = navHostController
            )
        }

        composable(route = Screens.WatchListDetailsScreen.route){backStackEntry ->
            val title = backStackEntry.arguments?.getString("title") ?:""
            val id = backStackEntry.arguments?.getString("id")?.toIntOrNull() ?: 0
            WatchListDetailsScreen(
                navController = navHostController,
                title = title ,
                id = id,
            )
        }

        composable(route = Screens.SearchScreen.route){
            SearchScreen(
                navController = navHostController
            )
        }
    }

}