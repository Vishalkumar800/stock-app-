package com.rach.stockapp.presentations.ui

import com.github.mikephil.charting.data.CandleEntry
import com.rach.stockapp.domain.model.StockDaily

fun mapToCandleEntries(timeSeries: Map<String, StockDaily>?): Pair<List<CandleEntry>, List<String>> {
    if (timeSeries == null) return Pair(emptyList(), emptyList())

    val sorted = timeSeries.entries.sortedBy { it.key } // date ascending
    val entries = sorted.mapIndexed { index, entry ->
        val daily = entry.value
        CandleEntry(
            index.toFloat(),
            daily.high.toFloat(),
            daily.low.toFloat(),
            daily.open.toFloat(),
            daily.close.toFloat()
        )
    }
    val dates = sorted.map { it.key } // list of dates (x-axis labels)

    return Pair(entries, dates)
}
