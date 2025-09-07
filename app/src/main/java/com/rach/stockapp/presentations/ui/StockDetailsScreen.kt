package com.rach.stockapp.presentations.ui

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rach.stockapp.R
import com.rach.stockapp.data.roomdb.SavedStockInfoEntity
import com.rach.stockapp.domain.model.CompanyOverviewModel
import com.rach.stockapp.presentations.theme.StockAppTheme
import com.rach.stockapp.presentations.theme.balooFont
import com.rach.stockapp.presentations.ui.components.CandleChartView
import com.rach.stockapp.presentations.ui.components.CustomErrorTextField
import com.rach.stockapp.presentations.ui.components.CustomLoadingProgressBar
import com.rach.stockapp.presentations.viewModels.CompanyInfoViewModel
import com.rach.stockapp.presentations.viewModels.UiEvent
import com.rach.stockapp.presentations.viewModels.WatchListViewModel
import com.rach.stockapp.utils.Resources


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockDetailsScreen(
    viewModel: CompanyInfoViewModel = hiltViewModel(),
    symbol: String,
    watchListViewModel: WatchListViewModel = hiltViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val companyInfoResult = viewModel.companyInfoState.collectAsState()
    val chartDataResult = viewModel.chartData.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.companyInfo(symbol)
    }

    LaunchedEffect(Unit) {
        viewModel.chartData(symbol)
    }

    LaunchedEffect(Unit) {
        watchListViewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    var showBottomSheet by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Details Screen",
                        fontFamily = balooFont,
                        fontWeight = FontWeight.Medium
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                actions = {
                    IconButton(onClick = {
                        showBottomSheet = true
                    }) {
                        Icon(
                            imageVector = Icons.Default.BookmarkBorder,
                            contentDescription = stringResource(R.string.book_mark_icon)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        when (val result = companyInfoResult.value) {
            is Resources.Loading -> {
                CustomLoadingProgressBar(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is Resources.Error -> {
                CustomErrorTextField(
                    errorMessage = result.message ?: "Error",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                )
            }

            is Resources.Success -> {
                StockDetailsUi(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .padding(paddingValues),
                    companyOverviewModel = result.data!!,
                    chartDataResult = chartDataResult.value,
                    showBottomSheet = showBottomSheet,
                    onDismissBottomState = {
                        showBottomSheet = false
                    },
                    viewModel = watchListViewModel
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StockDetailsUi(
    modifier: Modifier = Modifier,
    companyOverviewModel: CompanyOverviewModel,
    chartDataResult: Resources<com.rach.stockapp.domain.model.ChartModelData>,
    showBottomSheet: Boolean,
    onDismissBottomState: () -> Unit,
    viewModel: WatchListViewModel
) {
    val bottomSheetState = rememberModalBottomSheetState()
    var newWatchListName by remember { mutableStateOf("") }
    val context = LocalContext.current

    val watchListData = viewModel.watchList.collectAsState()
    var selectedWatchListById by remember { mutableStateOf<Int?>(null) }

    LazyColumn(modifier = modifier) {
        item {
            StockDetailsTopTitle(
                modifier = Modifier.fillMaxWidth(),
                companyModel = companyOverviewModel
            )
        }

        // 🔹 Graph Section
        item {
            Spacer(modifier = Modifier.height(10.dp))
            when (chartDataResult) {
                is Resources.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                MaterialTheme.colorScheme.surfaceVariant,
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is Resources.Error -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                            .background(
                                MaterialTheme.colorScheme.errorContainer,
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Failed to load chart",
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                    }
                }

                is Resources.Success -> {
                    val (entries, dates) = mapToCandleEntries(chartDataResult.data?.timeSeriesDaily)
                    CandleChartView(entries, dates)
                }
            }
        }

        // 🔹 About Section
        item {
            CompanyAboutSectionModern(
                companyModel = companyOverviewModel,
                modifier = Modifier
            )
        }
    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = onDismissBottomState,
            sheetState = bottomSheetState,
            dragHandle = null
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header
                Text(
                    text = "Add To WatchList",
                    fontFamily = balooFont,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                HorizontalDivider()

                Spacer(modifier = Modifier.height(16.dp))

                // Add new watchlist row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newWatchListName,
                        onValueChange = { newWatchListName = it },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        label = { Text("New WatchList Name") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Button(
                        onClick = {
                            if (newWatchListName.isNotEmpty()) {
                                viewModel.addWatchList(name = newWatchListName)
                                newWatchListName = ""
                            } else {
                                Toast.makeText(
                                    context,
                                    "Please Define WatchList Name",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Add")
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Existing watchlists
                Text(
                    text = "Your WatchLists",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                ) {
                    watchListData.value.forEach { item ->
                        SelectWatchListUi(
                            selected = item.id == selectedWatchListById,
                            text = item.title,
                            onRadioButtonClicked = { selectedWatchListById = item.id },
                            modifier = Modifier
                                .fillMaxWidth()
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Save button
                Button(
                    onClick = {
                        if (selectedWatchListById != null) {
                            viewModel.savedStock(
                                savedStockInfoEntity = SavedStockInfoEntity(
                                    stockName = companyOverviewModel.name,
                                    symbol = companyOverviewModel.symbol,
                                    watchListId = selectedWatchListById!!,
                                    stockPrice = companyOverviewModel.analystTargetPrice
                                )
                            )
                            onDismissBottomState()
                        } else {
                            Toast.makeText(
                                context,
                                "Please select a WatchList",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Text(
                        "Save Stock",
                        fontWeight = FontWeight.Bold,
                        fontFamily = balooFont
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }

}

@Composable
fun SelectWatchListUi(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onRadioButtonClicked: () -> Unit,
    text: String
) {
    Row(
        modifier = modifier
            .clickable { onRadioButtonClicked() }
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = { onRadioButtonClicked() }
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge.copy(
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
            ),
            color = if (selected) MaterialTheme.colorScheme.primary
            else MaterialTheme.colorScheme.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
private fun StockDetailsTopTitle(
    modifier: Modifier = Modifier,
    companyModel: CompanyOverviewModel
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(shape = CircleShape, color = MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = companyModel.name.takeIf { it.isNotEmpty() }?.first().toString()
                        ?: "A", // First letter of Name
                    color = Color.White,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }

            Column(
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text(
                    text = companyModel.name, // Company Name
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
                Row {
                    Text(
                        text = companyModel.symbol, // Symbol
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = " • ${companyModel.assetType}", // Asset Type
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
                Text(
                    text = companyModel.exchange, // Exchange
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }
        //
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "$ ${companyModel.analystTargetPrice}", // Current Price ( AnalystTargetPrice)
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
fun CompanyAboutSectionModern(
    companyModel: CompanyOverviewModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .background(
                color = Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .border(1.dp, Color.LightGray.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // Title
        Text(
            text = "About ${companyModel.name}",
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        )

        // Description
        Text(
            text = companyModel.description,
            style = MaterialTheme.typography.bodyMedium.copy(color = Color.DarkGray),
            modifier = Modifier.padding(top = 8.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        IndustryAndSectorChips(companyModel = companyModel)
        Spacer(modifier = Modifier.height(10.dp))

        Week52RangeSection(
            weekLow = "$ ${companyModel.weekLow}",
            weekHigh = "$ ${companyModel.weekHigh}",
            currentPrice = "$ ${companyModel.analystTargetPrice}"
        )

        // Market Cap / Dividend / P/E Ratio Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            InfoItem(label = "Mkt Cap", value = "$ ${companyModel.marketCapitalization}")
            InfoItem(label = "Dividend", value = "${companyModel.dividendYield}%")
            InfoItem(label = "P/E", value = companyModel.pERatio)
            InfoItem(label = "Beta", value = companyModel.beta)
            InfoItem(label = "ProfitMargin", value = companyModel.profitMargin)
        }
    }
}

@Composable
fun IndustryAndSectorChips(
    modifier: Modifier = Modifier,
    companyModel: CompanyOverviewModel,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Industry Chip
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(50)) // pill shape
                .background(Color(0xFFFFE0B2)) // light orange
                .padding(vertical = 6.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Industry : ${companyModel.industry}",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = Color(0xFFE65100), // deep orange text
                maxLines = 1
            )
        }

        // Sector Chip
        Box(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(50)) // pill shape
                .background(Color(0xFFFFE0B2)) // light orange
                .padding(vertical = 6.dp, horizontal = 12.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Sector : ${companyModel.sector}",
                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Medium),
                color = Color(0xFFE65100), // deep orange text
                maxLines = 1
            )
        }
    }
}


@Composable
fun Week52RangeSection(
    weekLow: String,
    weekHigh: String,
    currentPrice: String,
    modifier: Modifier = Modifier
) {
    val progress = 0.7f

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("52 Week Range", color = Color.Gray, style = MaterialTheme.typography.bodySmall)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // Base line
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp)
                    .background(Color.LightGray, RoundedCornerShape(2.dp))
            )

            // Progress till current
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress)
                    .height(4.dp)
                    .background(Color(0xFF4CAF50), RoundedCornerShape(2.dp))
            )

            // Current price marker (circle)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = (progress * (LocalConfiguration.current.screenWidthDp - 32)).dp) // adjust for padding
            ) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .background(Color.Black, CircleShape)
                )
            }
        }

        // Labels row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text("52 Week Low", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                Text(text = weekLow.toString(), color = Color.Red, fontWeight = FontWeight.Bold)
            }

            Column {
                Text("Current", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                Text(
                    text = currentPrice.toString(),
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text("52 Week High", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
                Text(
                    text = weekHigh.toString(),
                    color = Color(0xFF4CAF50),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


@Composable
private fun InfoItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = label,
            color = Color.Gray,
            style = MaterialTheme.typography.bodySmall
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun Preview() {
    StockAppTheme {
        StockDetailsScreen(
            symbol = "IBM"
        )
    }
}