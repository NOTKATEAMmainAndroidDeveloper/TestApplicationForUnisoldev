package com.ntk.testapplicationforunisoldev.acts.photo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import com.ntk.testapplicationforunisoldev.states.MakePhotoState

@Composable
fun TakePhoto(
    context: Context,
    photoState: MutableState<MakePhotoState>,
    onSuccess: (Bitmap?) -> (Unit)
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { it ->
        val source = it?.let { uriImage ->
            ImageDecoder
                .createSource(context.contentResolver, uriImage)
        }

        source?.let { it1 -> onSuccess(ImageDecoder.decodeBitmap(it1)) }

        photoState.value = MakePhotoState.NonAction
    }

    SideEffect {
        launcher.launch("image/*")
    }
}