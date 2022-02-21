package com.binar.githubuserapp.preference

import android.content.Context
import com.binar.githubuserapp.data.model.Reminder

class ReminderPreference(context: Context) {
    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(value: Reminder) {
        val edit = preference.edit()
        edit.putBoolean(REMINDER, value.isReminded)
        edit.apply()
    }

    fun getReminder(): Reminder {
        val model = Reminder()
        model.isReminded = preference.getBoolean(REMINDER, false)
        return model
    }

    companion object {
        const val PREFS_NAME = "reminder_pref"
        private const val REMINDER = "isReminder"
    }
}