package com.rach.stockapp.domain.model.search


import com.google.gson.annotations.SerializedName

data class SearchResultModel(
    @SerializedName("bestMatches")
    val bestMatches: List<BestMatches> = listOf()
)