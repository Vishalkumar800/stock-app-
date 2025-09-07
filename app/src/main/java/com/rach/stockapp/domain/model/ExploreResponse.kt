package com.rach.stockapp.domain.model
import com.google.gson.annotations.SerializedName

data class ExploreResponse(
    @SerializedName("top_gainers")
    val topGainers: List<TopGainer> = emptyList(),
    @SerializedName("top_losers")
    val topLosers: List<TopLoser> = emptyList()
)

data class TopGainer(
    @SerializedName("change_amount")
    val changeAmount: String = "",
    @SerializedName("change_percentage")
    val changePercentage: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("ticker")
    val ticker: String = "",
    @SerializedName("volume")
    val volume: String = ""
)

data class TopLoser(
    @SerializedName("change_amount")
    val changeAmount: String = "",
    @SerializedName("change_percentage")
    val changePercentage: String = "",
    @SerializedName("price")
    val price: String = "",
    @SerializedName("ticker")
    val ticker: String = "",
    @SerializedName("volume")
    val volume: String = ""
)
