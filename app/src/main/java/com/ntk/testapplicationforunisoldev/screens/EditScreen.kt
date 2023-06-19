package com.ntk.testapplicationforunisoldev.screens

import android.graphics.ColorMatrixColorFilter
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Colorize
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ntk.testapplicationforunisoldev.models.PathModel
import com.ntk.testapplicationforunisoldev.ui.theme.GrayLight
import com.ntk.testapplicationforunisoldev.ui.theme.Typography
import com.ntk.testapplicationforunisoldev.viewModels.MainViewModel
import com.ntk.testapplicationforunisoldev.views.dialogs.ColorAffectDialog
import com.ntk.testapplicationforunisoldev.views.dialogs.ColorPickerDialog
import com.ntk.testapplicationforunisoldev.views.dialogs.FichaDialogView


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    val view = LocalView.current
    val context = LocalView.current.context

    var cropSize: Offset? = Offset(0F, 0F)
    var imageSize: IntSize? = IntSize(0, 0)

    val drawColor = remember {
        mutableStateOf(Color(0,0,0))
    }

    val affectColor = remember {
        mutableStateOf(Color(255,255,255))
    }

    val isColorSelect = remember {
        mutableStateOf(false)
    }

    val isColorAffectSelect = remember {
        mutableStateOf(false)
    }


    val x = mutableStateOf(0)

    val blendMode = remember {
        mutableStateOf(BlendMode.Darken)
    }

    val action: MutableState<Any?> = mutableStateOf(null)
    val actionCount: MutableState<Int> = mutableStateOf(0)
    var path = Path()

    val imagePaths = mutableStateListOf<PathModel>()

    ColorPickerDialog(isOpen = isColorSelect, drawColor, mainViewModel)

    ColorAffectDialog(isOpen = isColorAffectSelect, affectColor, blendMode, mainViewModel, x)

    @Composable
    fun FunctionalRowView(){

        @Composable
        fun CustomButton(text: String, icon: ImageVector, size: Dp = 40.dp, action: () -> Unit){
            Box(
                modifier = Modifier
                    .clickable { action() }
            ) {
                Card(
                    modifier = Modifier
                        .size(size)
                        .align(Alignment.TopCenter),
                    shape = RoundedCornerShape(size),
                    colors = CardDefaults.cardColors(
                        containerColor = GrayLight
                    )
                ){
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ){
                        Icon(
                            modifier = Modifier
                                .size((size.value * 0.70).dp)
                                .align(Alignment.Center),
                            imageVector = icon,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }

                Text(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .align(Alignment.BottomCenter),
                    text = text,
                    style = Typography.labelSmall
                )
            }

        }


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            CustomButton("Выбор цвета", Icons.Default.Palette){
                isColorSelect.value = true
            }

            CustomButton("Наложение цвета", Icons.Default.Colorize){
                isColorAffectSelect.value = true
            }

            CustomButton("Убрать штрих", Icons.Default.Remove){
                if(imagePaths.isNotEmpty())imagePaths.remove(imagePaths.last())
            }

            CustomButton("Сохранить", Icons.Default.Save){
                mainViewModel.savePhoto(view, cropSize, imageSize, context)
            }

        }
    }

    if(mainViewModel.bitmap.value != null)Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ){
            mainViewModel.bitmap.value?.let {

                Box(modifier = Modifier
                    .fillMaxSize(0.8f)) {
                    x.value.let {
                        Image(
                            modifier = Modifier
                                .fillMaxSize()
                                .onGloballyPositioned {
                                    cropSize = it.positionInRoot()
                                    imageSize = it.size
                                }
                            ,
                            bitmap = mainViewModel.bitmap.value!!.asImageBitmap(),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(affectColor.value, blendMode = blendMode.value)
                        )
                    }

                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInteropFilter {
                                when (it.action) {
                                    MotionEvent.ACTION_DOWN -> {
                                        action.value = it
                                        path.moveTo(it.x, it.y)
                                        actionCount.value += 1
                                    }
                                    MotionEvent.ACTION_MOVE -> {
                                        action.value = it
                                        path.lineTo(
                                            if (it.x <= (imageSize!!.width + cropSize!!.x)) it.x else imageSize!!.width + cropSize!!.x,
                                            if (it.y <= (imageSize!!.height + cropSize!!.y)) it.y else imageSize!!.height + cropSize!!.y
                                        )

                                        actionCount.value += 1
                                    }
                                    MotionEvent.ACTION_UP -> {
                                        imagePaths.add(
                                            PathModel(
                                                path = path,
                                                color = drawColor.value,
                                                width = 10f
                                            )
                                        )

                                        path = Path()
                                        actionCount.value = 0
                                    }
                                }
                                true
                            },

                        onDraw = {
                            imagePaths.size.let {
                                for(item in imagePaths){
                                    drawPath(
                                        path = item.path,
                                        color = item.color,
                                        style = Stroke(item.width)
                                    )
                                }
                            }

                            actionCount.value.let {
                                drawPath(
                                    path = path,
                                    color = drawColor.value,
                                    style = Stroke(10f)
                                )

                            }
                        })

                }

            }


        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
        ){
            FunctionalRowView()
        }

    }

    if(mainViewModel.bitmap.value == null){
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text("Забыли фото выбрать! Не хорошо... Кстати если остаться на странице, то можно получить фичу в виде диалога о жизни от разработчика", style = Typography.titleLarge)

                FichaDialogView()
            }
        }
    }

}