package com.rach.stockapp.presentations.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rach.stockapp.presentations.theme.balooFont

@Composable
fun TitleAndViewAll(
    title: String,
    modifier: Modifier = Modifier,
    onViewAllClick: () -> Unit
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontFamily = balooFont,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Text(
            text = "View All",
            modifier = Modifier.clickable {
                onViewAllClick()
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = Color(0xFF1976D2), // Blue accent
                fontWeight = FontWeight.SemiBold
            )
        )
    }

}