package net.achris.mediumnothanks.ui.activity

import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.achris.mediumnothanks.model.ColorMode
import net.achris.mediumnothanks.store.Store

data class ActivityState(
    val colorMode: ColorMode
)

class ActivityViewModel(private val store: Store) : ViewModel() {

    private val activityState = MutableLiveData<ActivityState>()

    fun getActivityState(): LiveData<ActivityState> = activityState

    fun setInitialColorMode(applyAppTheme: Boolean) {
        setColorMode(store.loadColorMode())
        if (applyAppTheme) applyAppTheme()
    }

    fun toggleColorMode(): ColorMode? =
        activityState.value?.colorMode?.let {
            val newColorMode = switchColorMode(it)
            setColorMode(newColorMode)
            newColorMode
        }

    fun applyAppTheme() {
        activityState.value?.colorMode?.let {
            AppCompatDelegate.setDefaultNightMode(colorModeToAppTheme(it))
        }
    }

    private fun setColorMode(cm: ColorMode) {
        activityState.value = ActivityState(colorMode = cm)
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