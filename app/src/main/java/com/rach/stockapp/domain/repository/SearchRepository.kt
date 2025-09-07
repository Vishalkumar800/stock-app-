package com.rach.stockapp.domain.repository

import com.rach.stockapp.domain.model.search.SearchResultModel
import com.rach.stockapp.utils.Resources
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchResult(keyWord: String): Flow<Resources<SearchResultModel>>
}