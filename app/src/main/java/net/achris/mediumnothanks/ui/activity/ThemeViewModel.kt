package net.achris.mediumnothanks.ui.activity

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.achris.mediumnothanks.model.ColorMode
import net.achris.mediumnothanks.store.Store

data class ThemeState(
    val colorMode: ColorMode
)

class ThemeViewModel(private val store: Store) : ViewModel() {

    private val _themeLiveData = MutableLiveData<ThemeState>()
    val themeLiveData:LiveData<ThemeState>
        get() = _themeLiveData

    val currentColorModel: ColorMode?
        get() = _themeLiveData.value?.colorMode

    fun setInitialColorMode(applyAppTheme: Boolean) {
        setColorMode(store.loadColorMode())
        if (applyAppTheme) applyAppTheme()
    }

    fun toggleColorMode(): ColorMode? =
        _themeLiveData.value?.colorMode?.let {
            val newColorMode = switchColorMode(it)
            setColorMode(newColorMode)
            newColorMode
        }

    fun applyAppTheme() {
        _themeLiveData.value?.colorMode?.let {
            AppCompatDelegate.setDefaultNightMode(colorModeToAppTheme(it))
        }
    }

    private fun setColorMode(cm: ColorMode) {
        _themeLiveData.value = ThemeState(colorMode = cm)
        store.saveColorMode(cm)
    }

    private fun switchColorMode(cm: ColorMode) =
        if (cm == ColorMode.LIGHT)
            ColorMode.DARK
        else ColorMode.LIGHT

    private fun colorModeToAppTheme(cm: ColorMode) =
        if (cm == ColorMode.DARK)
            AppCompatDelegate.MODE_NIGHT_YES
        else
            AppCompatDelegate.MODE_NIGHT_NO
}