package com.jdsm.myapplication.presentation.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun BottomNavBar(
    checkItemSelected: (Any) -> Boolean,
    onNavItemClick: (Any) -> Unit
) {
    val navigationItems = getNavigationItems()

    NavigationBar {
        navigationItems.forEach { navItem ->
            val isItemSelected = checkItemSelected(navItem.destination)

            NavigationBarItem(
                selected = isItemSelected,
                label = { Text(navItem.title)},
                onClick = {
                    onNavItemClick(navItem.destination)
                },
                icon = {
                    Icon(
                        painter = navItem.selectedIcon,
                        contentDescription = navItem.title
                    )
                }
            )
        }
    }
}
