package com.ntk.testapplicationforunisoldev.viewModels

import android.app.Application
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.view.View
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.AndroidViewModel
import com.ntk.testapplicationforunisoldev.acts.photo.savePhoto
import kotlinx.coroutines.Dispatchers
import kotlin.concurrent.thread

class MainViewModel(application: Application) : AndroidViewModel(application) {
    var bitmap: MutableState<Bitmap?> = mutableStateOf(null)

    var recentColors = mutableStateListOf<Color>()

    fun savePhoto(view: View, cropSize: Offset?, imageSize: IntSize?, context: Context)  {
        Thread {
            val uri = view.savePhoto(cropSize!!, imageSize!!)

            val shareIntent = Intent(
                Intent.ACTION_SEND
            ).apply {
                type = "image/*"
                putExtra(Intent.EXTRA_STREAM, uri)
            }

            with(Dispatchers.Main){
                context.startActivity(
                    shareIntent
                )
            }
        }.start()
    }
}