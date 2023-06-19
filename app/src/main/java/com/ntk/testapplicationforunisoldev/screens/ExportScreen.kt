package com.ntk.testapplicationforunisoldev.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.IntSize
import androidx.navigation.NavHostController
import com.ntk.testapplicationforunisoldev.acts.photo.savePhoto
import com.ntk.testapplicationforunisoldev.navigation.CustomBottomNavigationView
import com.ntk.testapplicationforunisoldev.viewModels.MainViewModel

@Composable
fun ExportScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {

    val view = LocalView.current

    var cropSize: Offset? = Offset(0F, 0F)
    var imageSize: IntSize? = IntSize(0, 0)

    Column(modifier = Modifier.fillMaxWidth()) {
        if(mainViewModel.bitmap.value != null){
            Image(
                modifier = Modifier
                    .fillMaxSize(0.8f)
                    .onGloballyPositioned {
                        cropSize = it.positionInRoot()
                        imageSize = it.size
                    },
                bitmap = mainViewModel.bitmap.value!!.asImageBitmap(),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Green, blendMode = BlendMode.Darken)
            )
        }

        Button(onClick = {

            view.savePhoto(cropSize!!, imageSize!!)

        }) {
            Text(text = "Take Screenshot")
        }
    }

}