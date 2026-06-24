package com.example.mobile.ui.screens.settings

import android.content.Context
import android.content.SharedPreferences

object SettingsPreferences {
    private const val FILE_NAME = "nsync_settings"

    const val DAILY_GOAL = "daily_goal"
    const val REMINDER_TIME = "reminder_time"
    const val REVIEW_DIFFICULTY = "review_difficulty"
    const val STREAK_REMINDERS = "streak_reminders"
    const val NOTIFICATIONS_ENABLED = "notifications_enabled"

    fun from(context: Context): SharedPreferences =
        context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
}
