package com.rach.stockapp.domain.repository

import com.rach.stockapp.domain.model.ChartModelData
import com.rach.stockapp.domain.model.CompanyOverviewModel
import com.rach.stockapp.utils.Resources
import kotlinx.coroutines.flow.Flow

interface StockDetailsRepository {
    suspend fun companyOverView(symbol: String): Flow<Resources<CompanyOverviewModel>>
    suspend fun getChartData(symbol: String): Flow<Resources<ChartModelData>>

}