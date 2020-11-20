package com.github.kyrillosgait.gifnic.ui.common

import android.os.SystemClock
import android.view.View

/**
 * Makes a [View] visible.
 */
fun View.visible() {
    visibility = View.VISIBLE
}

/**
 * Makes a [View] invisible.
 */
fun View.invisible() {
    visibility = View.INVISIBLE
}

/**
 * Makes a [View] gone.
 */
fun View.gone() {
    visibility = View.GONE
}

/**
 * Doesn't let a [View] be clicked more than once every [debounceTime] ms.
 *
 * @param debounceTime the minimum time allowed between two consecutive clicks.
 * @param onViewClicked the lambda to be executed when onClick is triggered.
 */
inline fun View.onClick(
    debounceTime: Long = 667L,
    crossinline onViewClicked: () -> Unit
) {
    setOnClickListener(object : View.OnClickListener {
        private var lastClickTime = 0L

        override fun onClick(v: View) {
            if (SystemClock.elapsedRealtime() - lastClickTime < debounceTime) return

            onViewClicked()
            lastClickTime = SystemClock.elapsedRealtime()
        }
    })
}
