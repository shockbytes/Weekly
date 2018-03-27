package at.shockbytes.weekly.view

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Interpolator
import android.view.animation.Transformation

object ViewManager {

    private const val ANIMATION_TIME_SCALE = 1.5f

    @JvmOverloads
    fun expand(v: View, duration: Int = 0, interpolator: Interpolator? = null,
               stayInvisible: Boolean = false) {

        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        val targetHeight = v.measuredHeight
        v.layoutParams.height = 0
        val visibility = if (stayInvisible) View.INVISIBLE else View.VISIBLE
        v.visibility = visibility

        val a = object : Animation() {

            override fun applyTransformation(interpolatedTime: Float,
                                             t: Transformation) {
                v.layoutParams.height = if (interpolatedTime == 1f)
                    ViewGroup.LayoutParams.WRAP_CONTENT
                else
                    (targetHeight * interpolatedTime).toInt()
                v.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        if (interpolator != null) {
            a.interpolator = interpolator
        }
        if (duration <= 0) {
            a.duration = (ANIMATION_TIME_SCALE * targetHeight / v.context.resources.displayMetrics.density).toLong()
        } else {
            a.duration = duration.toLong()
        }

        v.startAnimation(a)
    }

    fun collapse(v: View?, listener: Animation.AnimationListener) {
        collapse(v, 0, null, listener)
    }

    private fun collapse(v: View?, duration: Int = 0,
                         interpolator: Interpolator? = null,
                         listener: Animation.AnimationListener? = null) {

        if (v == null || v.layoutParams == null) {
            return
        }

        val initialHeight = v.measuredHeight
        val a = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float,
                                             t: Transformation) {
                if (interpolatedTime == 1f) {
                    v.visibility = View.GONE
                } else {
                    v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
                    v.requestLayout()
                }
            }
            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        if (listener != null) {
            a.setAnimationListener(listener)
        }

        if (interpolator != null) {
            a.interpolator = interpolator
        }

        if (duration <= 0) {
            a.duration = (ANIMATION_TIME_SCALE * initialHeight / v.context.resources.displayMetrics.density).toLong()
        } else {
            a.duration = duration.toLong()
        }
        v.startAnimation(a)
    }

}
