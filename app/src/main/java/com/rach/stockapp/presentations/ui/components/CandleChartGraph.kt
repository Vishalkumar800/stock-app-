package com.rach.stockapp.presentations.ui.components

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.CandleStickChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.CandleData
import com.github.mikephil.charting.data.CandleDataSet
import com.github.mikephil.charting.data.CandleEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

@Composable
fun CandleChartView(entries: List<CandleEntry>, dates: List<String>) {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        factory = { context ->
            CandleStickChart(context).apply {
                val dataSet = CandleDataSet(entries, "Stock Price").apply {
                    decreasingColor = Color.RED
                    decreasingPaintStyle = Paint.Style.FILL
                    increasingColor = Color.GREEN
                    increasingPaintStyle = Paint.Style.FILL
                    neutralColor = Color.BLUE
                    shadowColor = Color.DKGRAY
                }
                data = CandleData(dataSet)

                xAxis.position = XAxis.XAxisPosition.BOTTOM
                xAxis.valueFormatter = IndexAxisValueFormatter(dates) // 🔑 Dates on X-axis
                xAxis.setLabelCount(5, true) // show ~5 labels only
                xAxis.granularity = 1f       // step size = 1

                axisRight.isEnabled = false
                description.isEnabled = false
                invalidate()
            }
        }
    )
}
