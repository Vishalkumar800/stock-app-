package com.rach.stockapp.data.repositoryImp

import android.util.Log
import com.rach.stockapp.data.network.ApiResponse
import com.rach.stockapp.domain.model.ExploreResponse
import com.rach.stockapp.domain.repository.Gainers
import com.rach.stockapp.utils.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GainerImp @Inject constructor(
    private val apiResponse: ApiResponse
) : Gainers {
    override suspend fun gainers(): Flow<Resources<ExploreResponse>> = flow {
        emit(Resources.Loading())
        try {
            val result = apiResponse.gainers()
            if (result.isSuccessful) {
                result.body()?.let { body ->
                    Log.d("API_RESPONSE","$body")
                    emit(Resources.Success(data = body))
                }?: emit(Resources.Error("Empty response body"))
            } else {
                emit(Resources.Error("Error Found ${result.message()}"))
            }
        } catch (e: Exception) {
            emit(Resources.Error(errorMessage = e.localizedMessage ?: "Unknown Error"))
        }
    }

}