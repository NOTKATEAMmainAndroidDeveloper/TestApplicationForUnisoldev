package com.ntk.testapplicationforunisoldev.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ntk.testapplicationforunisoldev.acts.photo.MakePhoto
import com.ntk.testapplicationforunisoldev.acts.photo.TakePhoto
import com.ntk.testapplicationforunisoldev.states.MakePhotoState
import com.ntk.testapplicationforunisoldev.ui.theme.GrayLight
import com.ntk.testapplicationforunisoldev.ui.theme.MainAppColor
import com.ntk.testapplicationforunisoldev.ui.theme.Typography
import com.ntk.testapplicationforunisoldev.viewModels.MainViewModel

@Composable
fun PhotoSelectorView(
    photoState: MutableState<MakePhotoState>,
    mainViewModel: MainViewModel,
    context: Context
) {
    @Composable
    fun CustomButton(text: String, icon: ImageVector, action: () -> Unit){
        Card(
            modifier = Modifier
                .size(125.dp)
                .padding(10.dp)
                .shadow(12.dp, spotColor = MainAppColor, ambientColor = MainAppColor)
                .clickable { action() },
            shape = RoundedCornerShape(21.dp),
            colors = CardDefaults.cardColors(
                containerColor = GrayLight
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    modifier = Modifier.
                        size(50.dp),
                    imageVector = icon,
                    tint = Color.White,
                    contentDescription = null
                )

                Text(
                    text = text,
                    style = Typography.bodySmall
                )
            }
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ){
        CustomButton(
            text = "Фото из галереи",
            icon = Icons.Default.Create
        ){
            photoState.value = MakePhotoState.TakePhoto
        }

        CustomButton(
            text = "Сделать фото",
            icon = Icons.Default.AddCircle
        ){
            photoState.value = MakePhotoState.MakePhoto
        }
    }

    when(photoState.value){
        MakePhotoState.MakePhoto -> {
            MakePhoto(
                photoState = photoState
            ){
                mainViewModel.bitmap.value = it
            }
        }
        MakePhotoState.TakePhoto -> {
            TakePhoto(
                context = context,
                photoState = photoState,
            ){
                mainViewModel.bitmap.value = it
            }
        }
        else -> {}
    }

}

@Composable
fun MainScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    val context = LocalContext.current

    val photoState: MutableState<MakePhotoState> = remember {
        mutableStateOf(MakePhotoState.NonAction)
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier.padding(16.dp)
        ){
            if(mainViewModel.bitmap.value != null){
                Text("Отличное фото!", style = Typography.titleLarge)
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Image(
                    bitmap = mainViewModel.bitmap.value!!.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier.size(400.dp)
                )
            }else{
                Text("Выберите фото и начнем работать!", style = Typography.titleLarge)
            }
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .align(Alignment.BottomCenter)) {
            PhotoSelectorView(photoState, mainViewModel, context)
        }

    }
}