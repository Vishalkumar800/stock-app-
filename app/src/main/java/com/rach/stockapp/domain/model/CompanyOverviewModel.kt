package com.rach.stockapp.domain.model
import com.google.gson.annotations.SerializedName

data class CompanyOverviewModel(
    @SerializedName("Address")
    val address: String = "",

    @SerializedName("AnalystRatingBuy")
    val analystRatingBuy: String = "",

    @SerializedName("AnalystRatingHold")
    val analystRatingHold: String = "",

    @SerializedName("AnalystRatingSell")
    val analystRatingSell: String = "",

    @SerializedName("AnalystRatingStrongBuy")
    val analystRatingStrongBuy: String = "",

    @SerializedName("AnalystRatingStrongSell")
    val analystRatingStrongSell: String = "",

    @SerializedName("AnalystTargetPrice")
    val analystTargetPrice: String = "",

    @SerializedName("AssetType")
    val assetType: String = "",

    @SerializedName("Beta")
    val beta: String = "",

    @SerializedName("BookValue")
    val bookValue: String = "",

    @SerializedName("CIK")
    val cIK: String = "",

    @SerializedName("Country")
    val country: String = "",

    @SerializedName("Currency")
    val currency: String = "",

    @SerializedName("50DayMovingAverage")
    val movingAverage50: String = "",

    @SerializedName("200DayMovingAverage")
    val movingAverage200: String = "",

    @SerializedName("Description")
    val description: String = "",

    @SerializedName("DilutedEPSTTM")
    val dilutedEPSTTM: String = "",

    @SerializedName("DividendDate")
    val dividendDate: String = "",

    @SerializedName("DividendPerShare")
    val dividendPerShare: String = "",

    @SerializedName("DividendYield")
    val dividendYield: String = "",

    @SerializedName("EBITDA")
    val eBITDA: String = "",

    @SerializedName("EPS")
    val ePS: String = "",

    @SerializedName("EVToEBITDA")
    val eVToEBITDA: String = "",

    @SerializedName("EVToRevenue")
    val eVToRevenue: String = "",

    @SerializedName("ExDividendDate")
    val exDividendDate: String = "",

    @SerializedName("Exchange")
    val exchange: String = "",

    @SerializedName("FiscalYearEnd")
    val fiscalYearEnd: String = "",

    @SerializedName("ForwardPE")
    val forwardPE: String = "",

    @SerializedName("GrossProfitTTM")
    val grossProfitTTM: String = "",

    @SerializedName("Industry")
    val industry: String = "",

    @SerializedName("LatestQuarter")
    val latestQuarter: String = "",

    @SerializedName("MarketCapitalization")
    val marketCapitalization: String = "",

    @SerializedName("Name")
    val name: String = "",

    @SerializedName("OfficialSite")
    val officialSite: String = "",

    @SerializedName("OperatingMarginTTM")
    val operatingMarginTTM: String = "",

    @SerializedName("PEGRatio")
    val pEGRatio: String = "",

    @SerializedName("PERatio")
    val pERatio: String = "",

    @SerializedName("PercentInsiders")
    val percentInsiders: String = "",

    @SerializedName("PercentInstitutions")
    val percentInstitutions: String = "",

    @SerializedName("PriceToBookRatio")
    val priceToBookRatio: String = "",

    @SerializedName("PriceToSalesRatioTTM")
    val priceToSalesRatioTTM: String = "",

    @SerializedName("ProfitMargin")
    val profitMargin: String = "",

    @SerializedName("QuarterlyEarningsGrowthYOY")
    val quarterlyEarningsGrowthYOY: String = "",

    @SerializedName("QuarterlyRevenueGrowthYOY")
    val quarterlyRevenueGrowthYOY: String = "",

    @SerializedName("ReturnOnAssetsTTM")
    val returnOnAssetsTTM: String = "",

    @SerializedName("ReturnOnEquityTTM")
    val returnOnEquityTTM: String = "",

    @SerializedName("RevenuePerShareTTM")
    val revenuePerShareTTM: String = "",

    @SerializedName("RevenueTTM")
    val revenueTTM: String = "",

    @SerializedName("Sector")
    val sector: String = "",

    @SerializedName("SharesFloat")
    val sharesFloat: String = "",

    @SerializedName("SharesOutstanding")
    val sharesOutstanding: String = "",

    @SerializedName("Symbol")
    val symbol: String = "",

    @SerializedName("TrailingPE")
    val trailingPE: String = "",

    @SerializedName("52WeekHigh")
    val weekHigh: String = "",

    @SerializedName("52WeekLow")
    val weekLow: String = ""
)
