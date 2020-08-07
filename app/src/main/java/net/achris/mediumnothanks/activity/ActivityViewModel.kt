package net.achris.mediumnothanks.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

enum class ColorMode {
    DARK, LIGHT;
}

data class ActivityState(
    val colorMode: ColorMode
)

class ActivityViewModel : ViewModel() {

    private val activityState = MutableLiveData<ActivityState>()

    fun getActivityState(): LiveData<ActivityState> = activityState

    fun setColorMode(cm: ColorMode) {
        activityState.value = ActivityState(colorMode = cm)
    }

    fun toggleColorMode() {
        val currentColorMode = activityState.value?.colorMode
        val nextColorMode =
            if (currentColorMode == ColorMode.LIGHT)
                ColorMode.DARK
            else
                ColorMode.LIGHT
        activityState.value = ActivityState(nextColorMode)
    }
}