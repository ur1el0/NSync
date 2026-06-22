package com.example.mobile.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.mobile.ui.screens.settings.SettingsPreferences
import java.util.Calendar

object ReviewReminderScheduler {
    private const val REQUEST_CODE = 401

    fun schedule(context: Context) {
        val preferences = SettingsPreferences.from(context)
        if (!preferences.getBoolean(SettingsPreferences.NOTIFICATIONS_ENABLED, true)) {
            cancel(context)
            return
        }

        val triggerAtMillis = reminderCalendar(
            preferences.getString(SettingsPreferences.REMINDER_TIME, "8:00 AM") ?: "8:00 AM"
        ).timeInMillis
        val alarmManager = context.getSystemService(AlarmManager::class.java)
        alarmManager.setAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent(context)
        )
    }

    fun cancel(context: Context) {
        context.getSystemService(AlarmManager::class.java).cancel(pendingIntent(context))
    }

    private fun reminderCalendar(reminderTime: String): Calendar {
        val (hour, minute) = when (reminderTime) {
            "12:00 PM" -> 12 to 0
            "6:00 PM" -> 18 to 0
            else -> 8 to 0
        }
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }
    }

    private fun pendingIntent(context: Context): PendingIntent = PendingIntent.getBroadcast(
        context,
        REQUEST_CODE,
        Intent(context, ReviewReminderReceiver::class.java),
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
}
