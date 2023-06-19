package com.ntk.testapplicationforunisoldev.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Share
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNav(
    var obj: NavHostRoute,
    var title: String,
    var icon: ImageVector
)

var BottomNavList = arrayOf(
    BottomNav(
        obj = NavHostRoute.Main,
        title = "Дом",
        icon = Icons.Default.Home
    ),

    BottomNav(
        obj = NavHostRoute.EditPhoto,
        title = "Редактирование",
        icon = Icons.Default.Edit
    )
)

sealed class NavHostRoute(val route: String){
    object Main: NavHostRoute("main_screen")
    object EditPhoto: NavHostRoute("edit_photo_screen")
}