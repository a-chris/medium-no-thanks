package net.achris.mediumnothanks.store

import android.content.Context
import net.achris.mediumnothanks.model.ColorMode

private const val THEME_KEY = "theme"

class Store(context: Context) {

    private val themePreferences = context.getSharedPreferences("theme.prefs", Context.MODE_PRIVATE)

    fun saveColorMode(cm: ColorMode) {
        themePreferences.edit().putString(THEME_KEY, cm.toString()).apply()
    }

    fun loadColorMode(): ColorMode {
        val storedValue = themePreferences.getString(THEME_KEY, ColorMode.LIGHT.toString())!!
        return ColorMode.fromString(storedValue)
    }

}