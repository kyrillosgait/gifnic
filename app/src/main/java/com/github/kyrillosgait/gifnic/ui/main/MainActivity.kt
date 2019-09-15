package com.github.kyrillosgait.gifnic.ui.main

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.github.kyrillosgait.gifnic.R
import com.github.kyrillosgait.gifnic.ui.common.setLightStatusBar

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val navController by lazy {
        Navigation.findNavController(this, R.id.mainNavHostFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_NO -> setLightStatusBar()
            else -> Unit
        }
    }

    override fun onSupportNavigateUp(): Boolean = navController.navigateUp()
}