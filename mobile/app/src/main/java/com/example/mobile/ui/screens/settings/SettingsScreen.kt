package com.example.mobile.ui.screens.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.example.mobile.R
import com.example.mobile.notifications.ReviewReminderScheduler
import com.example.mobile.data.SampleData
import com.example.mobile.ui.components.SettingsDivider
import com.example.mobile.ui.components.SettingsDropdownRow
import com.example.mobile.ui.components.SettingsRow
import com.example.mobile.ui.components.SettingsSection
import com.example.mobile.ui.components.SettingsToggleRow
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncCardWhite
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenCardBorder
import com.example.mobile.ui.theme.ScreenSectionStyle

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val context = LocalContext.current
    val preferences = remember(context) { SettingsPreferences.from(context) }
    val user = SampleData.userProfile
    val goals = listOf(10, 20, 30, 50, 100)
    val reminderTimes = listOf("8:00 AM", "12:00 PM", "6:00 PM")
    val difficulties = listOf("Adaptive", "Easy", "Medium", "Hard")

    var dailyGoal by rememberSaveable {
        mutableIntStateOf(preferences.getInt(SettingsPreferences.DAILY_GOAL, 50))
    }
    var reminderTime by rememberSaveable {
        mutableStateOf(preferences.getString(SettingsPreferences.REMINDER_TIME, "8:00 AM") ?: "8:00 AM")
    }
    var difficulty by rememberSaveable {
        mutableStateOf(preferences.getString(SettingsPreferences.REVIEW_DIFFICULTY, "Adaptive") ?: "Adaptive")
    }
    var streakReminders by rememberSaveable {
        mutableStateOf(preferences.getBoolean(SettingsPreferences.STREAK_REMINDERS, true))
    }
    var notificationsEnabled by rememberSaveable {
        mutableStateOf(preferences.getBoolean(SettingsPreferences.NOTIFICATIONS_ENABLED, true))
    }
    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        notificationsEnabled = granted
        preferences.edit().putBoolean(SettingsPreferences.NOTIFICATIONS_ENABLED, granted).apply()
        if (granted) ReviewReminderScheduler.schedule(context) else ReviewReminderScheduler.cancel(context)
    }

    LaunchedEffect(notificationsEnabled) {
        val hasNotificationPermission =
            Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
        if (notificationsEnabled && hasNotificationPermission) {
            ReviewReminderScheduler.schedule(context)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NSyncLightBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 28.dp, vertical = 18.dp),
        verticalArrangement = Arrangement.spacedBy(26.dp)
    ) {
        SettingsTopBar(onBackClick)

        SettingsSection(title = "ACCOUNT") {
            SettingsRow(
                label = "Profile",
                iconRes = R.drawable.ic_profile,
                onClick = onProfileClick,
                showChevron = true
            )
            SettingsDivider()
            SettingsRow(label = "Email Address", value = user.email, iconRes = R.drawable.ic_mail_outline)
            SettingsDivider()
            SettingsRow(label = "Change Password", value = "Unavailable", iconRes = R.drawable.ic_lock_outline)
        }

        SettingsSection(title = "REVIEW PREFERENCES") {
            SettingsDropdownRow(
                label = "Daily Review Goal",
                value = "$dailyGoal Cards",
                iconRes = R.drawable.ic_target,
                valueColor = NSyncBlue,
                options = goals.map { "$it Cards" },
                onOptionSelected = { selected ->
                    dailyGoal = selected.substringBefore(" ").toInt()
                    preferences.edit().putInt(SettingsPreferences.DAILY_GOAL, dailyGoal).apply()
                    if (notificationsEnabled) ReviewReminderScheduler.schedule(context)
                }
            )
            SettingsDivider()
            SettingsDropdownRow(
                label = "Reminder Time",
                value = reminderTime,
                iconRes = R.drawable.ic_note,
                options = reminderTimes,
                onOptionSelected = { selected ->
                    reminderTime = selected
                    preferences.edit().putString(SettingsPreferences.REMINDER_TIME, selected).apply()
                    if (notificationsEnabled) ReviewReminderScheduler.schedule(context)
                }
            )
            SettingsDivider()
            SettingsDropdownRow(
                label = "Review Difficulty",
                value = difficulty,
                iconRes = R.drawable.ic_flashcard,
                options = difficulties,
                onOptionSelected = { selected ->
                    difficulty = selected
                    preferences.edit().putString(SettingsPreferences.REVIEW_DIFFICULTY, selected).apply()
                }
            )
            SettingsDivider()
            SettingsToggleRow(
                label = "Streak Reminders",
                iconRes = R.drawable.ic_wind,
                checked = streakReminders,
                onCheckedChange = {
                    streakReminders = it
                    preferences.edit().putBoolean(SettingsPreferences.STREAK_REMINDERS, it).apply()
                    if (notificationsEnabled) ReviewReminderScheduler.schedule(context)
                }
            )
        }

        SettingsSection(title = "APP PREFERENCES") {
            SettingsToggleRow(
                label = "Notifications",
                iconRes = R.drawable.ic_note,
                checked = notificationsEnabled,
                onCheckedChange = {
                    if (!it) {
                        notificationsEnabled = false
                        preferences.edit().putBoolean(SettingsPreferences.NOTIFICATIONS_ENABLED, false).apply()
                        ReviewReminderScheduler.cancel(context)
                    } else if (
                        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                        ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.POST_NOTIFICATIONS
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                    } else {
                        notificationsEnabled = true
                        preferences.edit().putBoolean(SettingsPreferences.NOTIFICATIONS_ENABLED, true).apply()
                        ReviewReminderScheduler.schedule(context)
                    }
                }
            )
            SettingsDivider()
            SettingsRow(label = "Appearance", value = "Light", iconRes = R.drawable.ic_profile)
        }
    }
}

@Composable
private fun SettingsTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(top = 20.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_arrow_back),
            contentDescription = "Back",
            modifier = Modifier.clickable(onClick = onBackClick)
        )
        Text("NSync", color = NSyncBlue, style = ScreenSectionStyle)
        Text(" ", style = ScreenSectionStyle)
    }
}
