package net.achris.mediumnothanks.model

enum class ColorMode {
    DARK, LIGHT;

    companion object {
        fun fromString(name: String) = enumValueOf<ColorMode>(name)
    }
}