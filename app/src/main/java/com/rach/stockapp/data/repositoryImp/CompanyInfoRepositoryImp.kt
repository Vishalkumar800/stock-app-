package com.rach.stockapp.data.repositoryImp

import android.util.Log
import com.rach.stockapp.data.network.ApiResponse
import com.rach.stockapp.domain.model.ChartModelData
import com.rach.stockapp.domain.model.CompanyOverviewModel
import com.rach.stockapp.domain.repository.StockDetailsRepository
import com.rach.stockapp.utils.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompanyInfoRepositoryImp @Inject constructor(
    private val apiResponse: ApiResponse
) : StockDetailsRepository {
    override suspend fun companyOverView(symbol: String): Flow<Resources<CompanyOverviewModel>> =
        flow {

            emit(Resources.Loading())
            try {
                val response = apiResponse.companyOverview(symbol = symbol)
                if (response.isSuccessful) {
                    response.body()?.let { data ->
                        Log.d("API_RESPONSE2", "$data")
                        emit(Resources.Success(data = data))
                    }
                } else {
                    emit(Resources.Error(response.message()))
                }
            } catch (e: Exception) {
                emit(Resources.Error(e.localizedMessage ?: "Unknown Error"))
            }

        }

    override suspend fun getChartData(symbol: String): Flow<Resources<ChartModelData>> = flow {
        emit(Resources.Loading())
        try {
            val response = apiResponse.getDailySeries(symbol)
            if (response.isSuccessful) {
                response.body()?.let { data ->
                    emit(Resources.Success(data = data))
                }
            } else {
                emit(Resources.Error(response.message()))
            }
        } catch (e: Exception) {
            emit(Resources.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }


}