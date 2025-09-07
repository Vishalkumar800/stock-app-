package com.rach.stockapp.domain.model


import com.google.gson.annotations.SerializedName

data class StockDaily(
    @SerializedName("4. close")
    val close: String = "",
    @SerializedName("2. high")
    val high: String = "",
    @SerializedName("3. low")
    val low: String = "",
    @SerializedName("1. open")
    val `open`: String = "",
    @SerializedName("5. volume")
    val volume: String = ""
)