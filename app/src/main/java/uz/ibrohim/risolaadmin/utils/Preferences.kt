package uz.ibrohim.risolaadmin.utils

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    private lateinit var preferences: SharedPreferences

    fun init(context: Context) {
        preferences = context.getSharedPreferences("risola_caches", Context.MODE_PRIVATE)
    }

    fun setPreference(preferences: SharedPreferences) {
        Preferences.preferences = preferences
    }

    var uid: String
        get() = preferences.getString(Preferences::uid.name, "")!!
        set(value) {
            preferences.edit().putString(Preferences::uid.name, value).apply()
        }
}