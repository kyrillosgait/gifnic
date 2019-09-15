package com.github.kyrillosgait.gifnic.ui.common

import android.app.Activity
import android.view.View
import androidx.fragment.app.Fragment

fun Fragment.setLightStatusBar() {
    activity?.window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

fun Activity.setLightStatusBar() {
    window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
}

fun Fragment.setDarkStatusBar() {
    activity?.window?.decorView?.systemUiVisibility = 0
}