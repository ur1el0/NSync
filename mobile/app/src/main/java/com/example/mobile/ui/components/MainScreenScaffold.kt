package com.example.mobile.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenHeroStyle
import com.example.mobile.ui.theme.ScreenLogoStyle
import com.example.mobile.ui.theme.ScreenTitle

@Composable
fun MainScreenScaffold(
    currentRoute: String,
    title: String,
    subtitle: String,
    onRouteClick: (String) -> Unit,
    content: LazyListScope.() -> Unit
) {
    Scaffold(
        containerColor = NSyncLightBackground,
        bottomBar = {
            BottomNavBar(
                currentRoute = currentRoute,
                onRouteClick = onRouteClick
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp, vertical = 18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text("NSync", color = NSyncBlue, style = ScreenLogoStyle)
                Spacer(modifier = Modifier.height(16.dp))
                Text(title, color = ScreenTitle, style = ScreenHeroStyle)
                Text(subtitle, color = NSyncMutedText, style = ScreenBodyStyle)
            }
            content()
            item { Spacer(modifier = Modifier.height(74.dp)) }
        }
    }
}
