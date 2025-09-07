package com.rach.stockapp.domain.repository

import com.rach.stockapp.domain.model.ExploreResponse
import com.rach.stockapp.utils.Resources
import kotlinx.coroutines.flow.Flow

interface Gainers {
    suspend fun gainers(): Flow<Resources<ExploreResponse>>
}