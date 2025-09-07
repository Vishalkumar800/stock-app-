package com.rach.stockapp.domain.model


import com.google.gson.annotations.SerializedName

data class ChartModelData(
    @SerializedName("Meta Data")
    val metaData: MetaData = MetaData(),
    @SerializedName("Time Series (Daily)")
    val timeSeriesDaily: Map<String , StockDaily>? = null
)