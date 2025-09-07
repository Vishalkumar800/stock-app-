package com.rach.stockapp.presentations.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.rach.stockapp.presentations.navigation.Screens
import com.rach.stockapp.presentations.ui.components.SingleStockCard

interface Stock {
    val price: String
    val ticker: String
}

data class ReceiveTopGainer(
    override val price: String,
    override val ticker: String
) : Stock

data class ReceiveTopLosers(
    override val price: String,
    override val ticker: String
) : Stock

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopGainersAndLosersUi(
    type: String,
    stocks: List<Stock>,
    navController: NavController
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = if (type == "gainers") "Top Gainers" else "Top Losers") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                )
            )
        }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            columns = GridCells.Adaptive(150.dp),
        ) {
            items(stocks) {
                SingleStockCard(
                    modifier = Modifier.fillMaxWidth(),
                    stockPrice = it.price,
                    stockName = it.ticker,
                    onClick = {
                        navController.navigate(Screens.StockDetailsScreen.createRoute(it.ticker))
                    }
                )
            }
        }
    }

}