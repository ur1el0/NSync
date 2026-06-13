package com.example.mobile.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.mobile.navigation.bottomNavRoutes

@Composable
fun BottomNavBar(
    currentRoute: String,
    onRouteClick: (String) -> Unit
) {
    NavigationBar {
        bottomNavRoutes.forEach { item -> 
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    onRouteClick(item.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp)
                    )
                },
                label = {
                    Text(item.label)
                }
            )
        }
    }
}