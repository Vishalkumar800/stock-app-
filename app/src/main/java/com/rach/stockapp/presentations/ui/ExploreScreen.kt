package com.rach.stockapp.presentations.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.savedstate.savedState
import com.rach.stockapp.R
import com.rach.stockapp.presentations.navigation.Screens
import com.rach.stockapp.presentations.theme.balooFont
import com.rach.stockapp.presentations.ui.components.BottomAppBarByMe
import com.rach.stockapp.presentations.ui.components.CustomErrorTextField
import com.rach.stockapp.presentations.ui.components.CustomLoadingProgressBar
import com.rach.stockapp.presentations.ui.components.CustomTopAppBar
import com.rach.stockapp.presentations.ui.components.SingleStockCard
import com.rach.stockapp.presentations.ui.components.TitleAndViewAll
import com.rach.stockapp.presentations.viewModels.TopGainerAndLosersViewModel
import com.rach.stockapp.utils.Resources


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExploreScreen(
    viewModel: TopGainerAndLosersViewModel = hiltViewModel(),
    navController: NavController
) {

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val gainerState = viewModel.gainerState.collectAsState()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                modifier = Modifier.fillMaxWidth(),
                title = {
                    Text(
                        text = "Stock App", fontFamily = balooFont,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screens.SearchScreen.route){
                                restoreState = true
                                launchSingleTop = true
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                             contentDescription = stringResource(R.string.search_icons)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBarByMe(
                navController = navController
            )
        }
    ) { paddingValues ->

        when (val result = gainerState.value) {

            is Resources.Loading -> {
                CustomLoadingProgressBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is Resources.Success -> {
                val uiLosers = result.data?.topLosers?.map {
                    ReceiveTopLosers(
                        price = it.price,
                        ticker = it.ticker
                    )
                } ?: emptyList()

                val uiGainers = result.data?.topGainers?.map {
                    ReceiveTopGainer(
                        price = it.price,
                        ticker = it.ticker
                    )
                } ?: emptyList()
                HomeScreenTopGainerAndLosers(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(paddingValues),
                    topLosers = uiLosers,
                    topGainers = uiGainers,
                    navController = navController
                )
            }

            is Resources.Error -> {
                CustomErrorTextField(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    errorMessage = result.message!!
                )
            }

        }
    }

}

@Composable
private fun HomeScreenTopGainerAndLosers(
    modifier: Modifier = Modifier,
    topGainers: List<Stock>,
    topLosers: List<Stock>,
    navController: NavController
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Adaptive(150.dp)
    ) {
        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            TitleAndViewAll(
                "Top Gainers",
                onViewAllClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("stocks", topGainers)
                    navController.navigate(Screens.TopGainerAndLosers.createRoute("gainers"))
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        items(topGainers.take(4)) { stock ->
            SingleStockCard(
                stockName = stock.ticker,
                stockPrice = stock.price,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(Screens.StockDetailsScreen.createRoute(stock.ticker))
                }
            )
        }

        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            Spacer(modifier = Modifier.height(20.dp))
        }

        item(span = { GridItemSpan(maxCurrentLineSpan) }) {
            TitleAndViewAll(
                "Top Losers",
                onViewAllClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set("stocks", topLosers)
                    navController.navigate(Screens.TopGainerAndLosers.createRoute("losers"))
                },
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        items(topLosers.take(4)) { stock ->
            SingleStockCard(
                stockName = stock.ticker,
                stockPrice = stock.price,
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    navController.navigate(Screens.StockDetailsScreen.createRoute(stock.ticker))
                }
            )
        }

    }
}