package com.example.mobile.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mobile.R
import com.example.mobile.data.SampleData
import com.example.mobile.navigation.Routes
import com.example.mobile.ui.components.MainScreenScaffold
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenCardBorder
import com.example.mobile.ui.theme.ScreenHeroStyle
import com.example.mobile.ui.theme.ScreenSectionStyle
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncCardWhite
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.NSyncRed
import com.example.mobile.ui.viewmodel.ProfileViewModel
import kotlin.math.roundToInt

@Composable
fun ProfileScreen(
    onRouteClick: (String) -> Unit,
    onSettingsClick: () -> Unit,
    onLogoutClick: () -> Unit,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val user = SampleData.userProfile
    val uiState = profileViewModel.uiState
    val progress = uiState.progress
    val totalXp = progress?.totalXp ?: 0
    val totalReviews = progress?.totalReviews ?: 0
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, profileViewModel) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                profileViewModel.loadProfile()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    MainScreenScaffold(
        currentRoute = Routes.PROFILE,
        title = "User Profile",
        subtitle = user.learningGoal,
        onRouteClick = onRouteClick
    ) {
        if (uiState.isLoading) {
            item { Text("Loading profile...", color = NSyncMutedText, style = ScreenBodyStyle) }
        }

        uiState.error?.let { message ->
            item {
                Text(
                    "Unable to load profile: $message",
                    color = NSyncRed,
                    style = ScreenBodyStyle
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
                shape = RoundedCornerShape(18.dp),
                border = ScreenCardBorder
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFE8F1FF)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(user.name.take(1), color = NSyncBlue, style = ScreenHeroStyle)
                    }
                    Text(user.name, color = Color(0xFF151927), style = ScreenSectionStyle)
                    Text(user.email, color = NSyncMutedText, style = ScreenBodyStyle)
                    Text(user.learningGoal, color = NSyncMutedText, style = ScreenBodyStyle)
                    Text("LVL ${progress?.level ?: 1}", color = NSyncBlue, style = ScreenSectionStyle)
                    Text(
                        "$totalXp XP - $totalReviews cards reviewed",
                        color = NSyncMutedText,
                        style = ScreenBodyStyle
                    )
                }
            }
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    icon = R.drawable.ic_wind,
                    iconTint = NSyncRed,
                    value = (progress?.streak ?: 0).toString(),
                    label = "Day streak"
                )
                ProfileStatCard(
                    modifier = Modifier.weight(1f),
                    icon = R.drawable.ic_target,
                    iconTint = NSyncBlue,
                    value = "${progress?.accuracy?.roundToInt() ?: 0}%",
                    label = "Accuracy"
                )
            }
        }

        item {
            OutlinedButton(
                onClick = onSettingsClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Settings", color = NSyncBlue, style = ScreenSectionStyle)
            }
        }

        item {
            Button(
                onClick = onLogoutClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(999.dp),
                colors = ButtonDefaults.buttonColors(containerColor = NSyncRed)
            ) {
                Text("Logout", color = Color.White, style = ScreenSectionStyle)
            }
        }
    }
}

@Composable
private fun ProfileStatCard(
    modifier: Modifier,
    icon: Int,
    iconTint: Color,
    value: String,
    label: String
) {
    Card(
        modifier = modifier.height(88.dp),
        colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
        shape = RoundedCornerShape(10.dp),
        border = ScreenCardBorder
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(26.dp),
                tint = iconTint
            )
            Text(value, color = Color(0xFF151927), style = ScreenSectionStyle)
            Text(label, color = NSyncMutedText, style = ScreenBodyStyle)
        }
    }
}
