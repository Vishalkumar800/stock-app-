package com.rach.stockapp.presentations.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

@Composable
fun RouteScreen() {

    val navController = rememberNavController()

    AppNavigation(
        navHostController = navController
    )

}