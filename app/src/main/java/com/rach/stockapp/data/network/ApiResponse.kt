package com.rach.stockapp.data.network

import com.rach.stockapp.domain.model.ChartModelData
import com.rach.stockapp.domain.model.CompanyOverviewModel
import com.rach.stockapp.domain.model.ExploreResponse
import com.rach.stockapp.domain.model.search.SearchResultModel
import com.rach.stockapp.utils.K
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiResponse {

    @GET("query?function=TOP_GAINERS_LOSERS")
    suspend fun gainers(
        @Query("apikey") apiKey: String = K.API_KEY
    ): Response<ExploreResponse>

    @GET("query?function=OVERVIEW")
    suspend fun companyOverview(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = K.API_KEY
    ): Response<CompanyOverviewModel>

    @GET("query?function=TIME_SERIES_DAILY")
    suspend fun getDailySeries(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = K.API_KEY
    ): Response<ChartModelData>

    @GET("query?function=SYMBOL_SEARCH")
    suspend fun searchResult(
        @Query("keywords") keywords: String,
        @Query("apikey") apiKey: String = K.API_KEY
    ): Response<SearchResultModel>

}