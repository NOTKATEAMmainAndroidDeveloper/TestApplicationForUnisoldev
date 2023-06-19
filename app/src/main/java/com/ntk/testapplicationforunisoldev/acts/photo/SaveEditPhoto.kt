package com.ntk.testapplicationforunisoldev.acts.photo

import android.app.Activity
import android.app.Application
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.Environment
import android.os.Handler
import android.os.Looper
import android.view.PixelCopy
import android.view.View
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.core.content.FileProvider
import androidx.core.graphics.applyCanvas
import androidx.core.net.toUri
import com.ntk.testapplicationforunisoldev.BuildConfig
import com.ntk.testapplicationforunisoldev.MainActivity
import java.io.File
import kotlin.concurrent.thread

fun View.savePhoto(cropSize: Offset, imageSize: IntSize): Uri {

    fun crop(newBitmap: Bitmap): Bitmap{
        return Bitmap.createBitmap(
            newBitmap,
            cropSize.x.toInt(),
            cropSize.y.toInt(),
            imageSize.width,
            imageSize.height,
        )
    }

    fun save(newBitmap: Bitmap): Uri{
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)

        if (path != null) {
            if(!path.exists()) path.mkdir()
        }

        val fileToSave = File(path, "screenshot" + System.currentTimeMillis() + ".jpeg")

        newBitmap.let {
            fileToSave.writeBitmap(newBitmap, Bitmap.CompressFormat.JPEG, 100)

            return FileProvider.getUriForFile(
                context,
                BuildConfig.APPLICATION_ID +".provider", //(use your app signature + ".provider" )
                fileToSave)
        }
    }

    val bitmap = Bitmap.createBitmap(this.width, this.height, Bitmap.Config.ARGB_8888)
    val location = IntArray(2)
    this.getLocationInWindow(location)

    PixelCopy.request(MainActivity.windowActivity,
        Rect(
            location[0], location[1], location[0] + this.width, location[1] + this.height),
        bitmap,
        {

        },
        Handler(Looper.getMainLooper()) )

    return save(crop(bitmap))
}