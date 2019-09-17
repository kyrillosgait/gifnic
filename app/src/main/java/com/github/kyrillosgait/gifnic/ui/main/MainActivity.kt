package com.github.kyrillosgait.gifnic.ui.main

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.github.kyrillosgait.gifnic.R

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController by lazy {
        Navigation.findNavController(this, R.id.mainNavHostFragment)
    }

    private val drawBehindSystemBars: () -> Unit = {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
                            View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
            else -> {
                window.decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        drawBehindSystemBars()
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}