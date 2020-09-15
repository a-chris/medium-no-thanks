package net.achris.mediumnothanks.extensions

import android.view.View

const val FLIP_IN = -1F
const val FLIP_OUT = 1F

fun View.toggleFlip() {
    scaleX = if (scaleX == FLIP_OUT) FLIP_IN else FLIP_OUT
}

fun View.flipIn() {
    scaleX = FLIP_IN
}

fun View.flipOut() {
    scaleX = FLIP_OUT
}

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}