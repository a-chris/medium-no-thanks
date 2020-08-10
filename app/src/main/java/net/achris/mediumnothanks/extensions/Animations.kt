package net.achris.mediumnothanks.extensions

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.TimeInterpolator
import android.view.View
import android.view.animation.AccelerateInterpolator

const val ANIM_alpha_CONST = "alpha"
const val ANIM_rotationY_CONST = "rotationY"

fun AnimatorSet.playAnimation(
    animationDuration: Long = 500L,
    customInterpolator: TimeInterpolator = AccelerateInterpolator()
): AnimatorSet {
    interpolator = customInterpolator
    duration = animationDuration
    start()
    return this
}

fun View.animateFlipY(): AnimatorSet {
    val animatorSet = AnimatorSet()
    val object1 = ObjectAnimator.ofFloat(this, ANIM_alpha_CONST, 1f, 0.25f)
    val object2 = ObjectAnimator.ofFloat(this, ANIM_rotationY_CONST, 0f, 90f)
    animatorSet.playTogether(object1, object2)
    return animatorSet
}