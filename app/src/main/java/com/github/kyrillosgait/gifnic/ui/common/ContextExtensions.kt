package com.github.kyrillosgait.gifnic.ui.common

import android.app.Activity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

/**
 * Makes the status bar icons and text appear dark. To be used with light colors (eg. white).
 */
fun Activity.setLightStatusBar() {
    window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

/**
 * Shows a simple [Toast].
 *
 * @param message the message to be displayed.
 * @param duration the duration of the toast. Defaults to [Toast.LENGTH_SHORT].
 */
fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(requireContext(), message, duration).show()
}