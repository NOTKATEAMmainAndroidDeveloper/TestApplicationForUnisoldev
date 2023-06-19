package com.ntk.testapplicationforunisoldev

import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.ntk.testapplicationforunisoldev.navigation.CustomBottomNavigationView
import com.ntk.testapplicationforunisoldev.navigation.MainNavigation
import com.ntk.testapplicationforunisoldev.ui.theme.DarkColorScheme
import com.ntk.testapplicationforunisoldev.ui.theme.TestApplicationForUnisoldevTheme
import com.ntk.testapplicationforunisoldev.viewModels.MainViewModel

class MainActivity : ComponentActivity() {
    companion object{
        lateinit var windowActivity: Window
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        windowActivity = window

        ViewModelProvider.AndroidViewModelFactory.getInstance(application).create(MainViewModel::class.java)

        setContent {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                MainNavigation()
            }
        }
    }
}