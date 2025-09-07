package com.rach.stockapp.presentations.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CustomErrorTextField(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    errorMessage: String
) {

    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ){
        Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error
        )
    }

}