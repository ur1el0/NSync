package com.example.mobile.ui.screens.settings

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobile.data.SampleData
import com.example.mobile.ui.theme.NSyncBlue
import com.example.mobile.ui.theme.NSyncCardWhite
import com.example.mobile.ui.theme.NSyncLightBackground
import com.example.mobile.ui.theme.NSyncMutedText
import com.example.mobile.ui.theme.ScreenBodyStyle
import com.example.mobile.ui.theme.ScreenCardBorder
import com.example.mobile.ui.theme.ScreenSectionStyle

private const val SETTINGS_PREFERENCES = "nsync_settings"

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val context = LocalContext.current
    val preferences = remember(context) {
        context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE)
    }
    val user = SampleData.userProfile
    val goals = listOf(10, 20, 30, 50, 100)
    val reminderTimes = listOf("8:00 AM", "12:00 PM", "6:00 PM")
    val difficulties = listOf("Adaptive", "Easy", "Medium", "Hard")

    var dailyGoal by rememberSaveable { mutableIntStateOf(preferences.getInt("daily_goal", 50)) }
    var reminderTime by rememberSaveable {
        mutableStateOf(preferences.getString("reminder_time", "8:00 AM") ?: "8:00 AM")
    }
    var difficulty by rememberSaveable {
        mutableStateOf(preferences.getString("review_difficulty", "Adaptive") ?: "Adaptive")
    }
    var streakReminders by rememberSaveable {
        mutableStateOf(preferences.getBoolean("streak_reminders", true))
    }
    var notificationsEnabled by rememberSaveable {
        mutableStateOf(preferences.getBoolean("notifications_enabled", true))
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
            SettingsRow(label = "Profile", value = ">", onClick = onProfileClick)
            SettingsDivider()
            SettingsRow(label = "Email Address", value = user.email)
            SettingsDivider()
            SettingsRow(label = "Change Password", value = "Unavailable")
        }

        SettingsSection(title = "REVIEW PREFERENCES") {
            SettingsRow(
                label = "Daily Review Goal",
                value = "$dailyGoal Cards",
                valueColor = NSyncBlue,
                onClick = {
                    dailyGoal = nextValue(goals, dailyGoal)
                    preferences.edit().putInt("daily_goal", dailyGoal).apply()
                }
            )
            SettingsDivider()
            SettingsRow(
                label = "Reminder Time",
                value = reminderTime,
                onClick = {
                    reminderTime = nextValue(reminderTimes, reminderTime)
                    preferences.edit().putString("reminder_time", reminderTime).apply()
                }
            )
            SettingsDivider()
            SettingsRow(
                label = "Review Difficulty",
                value = difficulty,
                onClick = {
                    difficulty = nextValue(difficulties, difficulty)
                    preferences.edit().putString("review_difficulty", difficulty).apply()
                }
            )
            SettingsDivider()
            SettingsToggleRow(
                label = "Streak Reminders",
                checked = streakReminders,
                onCheckedChange = {
                    streakReminders = it
                    preferences.edit().putBoolean("streak_reminders", it).apply()
                }
            )
        }

        SettingsSection(title = "APP PREFERENCES") {
            SettingsToggleRow(
                label = "Notifications",
                checked = notificationsEnabled,
                onCheckedChange = {
                    notificationsEnabled = it
                    preferences.edit().putBoolean("notifications_enabled", it).apply()
                }
            )
            SettingsDivider()
            SettingsRow(label = "Appearance", value = "System")
        }
    }
}

@Composable
private fun SettingsTopBar(onBackClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "<",
            color = NSyncMutedText,
            style = ScreenSectionStyle,
            modifier = Modifier.clickable(onClick = onBackClick)
        )
        Text("NSync", color = NSyncBlue, style = ScreenSectionStyle)
        Text(" ", style = ScreenSectionStyle)
    }
}

@Composable
private fun SettingsSection(
    title: String,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text(
            text = title,
            color = Color(0xFF3D4556),
            style = ScreenBodyStyle,
            fontWeight = FontWeight.SemiBold
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = NSyncCardWhite),
            shape = RoundedCornerShape(14.dp),
            border = ScreenCardBorder
        ) {
            Column(content = { content() })
        }
    }
}

@Composable
private fun SettingsRow(
    label: String,
    value: String,
    valueColor: Color = NSyncMutedText,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .padding(horizontal = 18.dp, vertical = 18.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color(0xFF202431), style = ScreenSectionStyle)
        Text(value, color = valueColor, style = ScreenBodyStyle)
    }
}

@Composable
private fun SettingsToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, color = Color(0xFF202431), style = ScreenSectionStyle)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun SettingsDivider() {
    HorizontalDivider(color = Color(0xFFE0E4ED))
}

private fun <T> nextValue(values: List<T>, current: T): T {
    val currentIndex = values.indexOf(current)
    return values[(currentIndex + 1).mod(values.size)]
}
