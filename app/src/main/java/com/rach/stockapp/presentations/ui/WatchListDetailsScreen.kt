package com.rach.stockapp.presentations.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.rach.stockapp.presentations.navigation.Screens
import com.rach.stockapp.presentations.ui.components.CustomErrorTextField
import com.rach.stockapp.presentations.ui.components.CustomTopAppBar
import com.rach.stockapp.presentations.ui.components.SingleStockCard
import com.rach.stockapp.presentations.viewModels.WatchListViewModel

@Composable
fun WatchListDetailsScreen(
    id: Int,
    title: String,
    navController: NavController,
    viewModel: WatchListViewModel = hiltViewModel()
) {

    val savedStocks by viewModel.stock.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getAllSavedStockByWatchListId(watchListId = id)
    }

    Scaffold(
        topBar = {
            CustomTopAppBar(
                title = title,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { paddingValues ->
        if (savedStocks.isEmpty()) {
            CustomErrorTextField(
                modifier = Modifier.fillMaxSize(),
                errorMessage = "Not Stocks Found"
            )
        } else {
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .padding(paddingValues),
                columns = GridCells.Adaptive(150.dp)
            ) {
                items(savedStocks) { item ->
                    SingleStockCard(
                        stockName = item.stockName,
                        stockPrice = item.stockPrice,
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            navController.navigate(Screens.StockDetailsScreen.createRoute(item.symbol))
                        }
                    )
                }
            }
        }
    }

}