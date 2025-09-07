package com.rach.stockapp.data.repositoryImp

import com.rach.stockapp.data.network.ApiResponse
import com.rach.stockapp.domain.model.search.SearchResultModel
import com.rach.stockapp.domain.repository.SearchRepository
import com.rach.stockapp.utils.Resources
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImp @Inject constructor(
    private val apiResponse: ApiResponse
): SearchRepository {
    override suspend fun searchResult(keyWord: String): Flow<Resources<SearchResultModel>> = flow {
        emit(Resources.Loading())
        try {
            val response = apiResponse.searchResult(keywords = keyWord)
            if (response.isSuccessful){
                response.body()?.let {body ->
                    emit(Resources.Success(body))
                }
            }else{
                emit(Resources.Error(response.message()))
            }
        }catch (e: Exception){
            emit(Resources.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }
}