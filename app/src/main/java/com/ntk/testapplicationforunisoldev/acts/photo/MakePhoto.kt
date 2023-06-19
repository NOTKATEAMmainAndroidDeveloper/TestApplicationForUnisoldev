package com.ntk.testapplicationforunisoldev.acts.photo

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import com.ntk.testapplicationforunisoldev.states.MakePhotoState

@Composable
fun MakePhoto(
    photoState: MutableState<MakePhotoState>,
    onSuccess: (Bitmap?) -> (Unit)
) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) {
        if(it != null){
            onSuccess(it)
        }

        photoState.value = MakePhotoState.NonAction
    }

    SideEffect {
        launcher.launch()
    }
}