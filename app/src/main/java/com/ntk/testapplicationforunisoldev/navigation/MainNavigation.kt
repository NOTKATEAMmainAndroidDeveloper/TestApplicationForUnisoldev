package com.ntk.testapplicationforunisoldev.navigation

import android.app.Application
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ntk.testapplicationforunisoldev.screens.EditScreen
import com.ntk.testapplicationforunisoldev.screens.ExportScreen
import com.ntk.testapplicationforunisoldev.screens.MainScreen
import com.ntk.testapplicationforunisoldev.ui.theme.Typography
import com.ntk.testapplicationforunisoldev.viewModels.MainViewModel
import com.ntk.testapplicationforunisoldev.viewModels.ViewModelFactory

@Composable
fun MainNavigation() {
    val navCont = rememberNavController()

    val mainViewModel: MainViewModel = viewModel(
        factory = ViewModelFactory(LocalContext.current.applicationContext as Application)
    )

    NavHost(
        modifier = Modifier.padding(bottom = 75.dp),
        navController = navCont,
        startDestination = NavHostRoute.Main.route
    ) {
        composable(NavHostRoute.Main.route){ MainScreen(navHostController = navCont, mainViewModel) }
        composable(NavHostRoute.EditPhoto.route){ EditScreen(navHostController = navCont, mainViewModel) }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ){
            CustomBottomNavigationView(navHostController = navCont)
        }
    }
}