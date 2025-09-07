package com.rach.stockapp.presentations.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Bookmarks
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rach.stockapp.presentations.navigation.Screens

@Composable
fun BottomAppBarByMe(
    modifier: Modifier = Modifier,
    navController: NavController
) {

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier
    ) {
        CustomBottomAppBar().bottomAppBar().forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route){
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.title
                    )
                },
                label = {
                    item.title?.let { Text(it) }
                }
            )
        }
    }
}

data class CustomBottomAppBar(
    val title: String? = null,
    val icon: ImageVector = Icons.Outlined.Home,
    val route: String = ""
) {
    fun bottomAppBar(): List<CustomBottomAppBar> {
        return listOf(
            CustomBottomAppBar(
                "Home",
                Icons.Outlined.Home,
                Screens.HomeScreen.route
            ),
            CustomBottomAppBar(
                "Watchlist",
                Icons.Outlined.Bookmarks,
                Screens.WatchListScreen.route
            )
        )
    }
}