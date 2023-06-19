package com.ntk.testapplicationforunisoldev.views.dialogs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.ntk.testapplicationforunisoldev.ui.theme.GrayDark
import com.ntk.testapplicationforunisoldev.ui.theme.Typography
import com.ntk.testapplicationforunisoldev.viewModels.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorAffectDialog(
    isOpen: MutableState<Boolean>,
    drawColor: MutableState<Color>,
    blendMode: MutableState<BlendMode>,
    mainViewModel: MainViewModel,
    x: MutableState<Int>,
) {

    val typeOfBlendMode = arrayListOf(BlendMode.Darken, BlendMode.Color, BlendMode.Hardlight, BlendMode.Lighten, BlendMode.Multiply, BlendMode.Screen, BlendMode.Softlight)

    fun readebleColorCount(count: Float): String{
        return (255 * count).toInt().toString()
    }

    if(isOpen.value) AlertDialog(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f)
            .clip(RoundedCornerShape(16.dp)),
        onDismissRequest = {
            isOpen.value = false
        },
    ) {
        var expanded by remember { mutableStateOf(false) }

        var redValue by remember { mutableStateOf(drawColor.value.red) }
        var greenValue by remember { mutableStateOf(drawColor.value.green) }
        var blueValue by remember { mutableStateOf(drawColor.value.blue) }

        var newColor by remember {
            mutableStateOf(Color(drawColor.value.red, drawColor.value.green, drawColor.value.blue))
        }

        val newBlendModeCopy = blendMode.value

        var newBlend by remember {
            mutableStateOf(newBlendModeCopy)
        }

        Box(modifier = Modifier
            .background(GrayDark)
            .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .align(Alignment.TopStart)
            ){

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ){
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(250.dp),
                        bitmap = mainViewModel.bitmap.value!!.asImageBitmap(),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(newColor, blendMode = newBlend)
                    )

                    Text(
                        modifier = Modifier
                            .clickable {
                                expanded = true
                            },
                        text = "Режим наложения: ${newBlend.toString()}",
                        style = Typography.titleLarge
                    )

                    IconButton(onClick = { expanded = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Показать меню", tint = Color.White)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        offset = DpOffset(x = 20.dp, y = 10.dp)
                    ) {

                        for(type in typeOfBlendMode){
                            DropdownMenuItem(onClick = {
                                newBlend = type
                            }, text = { Text(type.toString()) })
                        }

                    }

                }

            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row {
                    Text(
                        text = readebleColorCount(redValue),
                        style = Typography.bodySmall
                    )

                    Slider(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        value = redValue,
                        onValueChange = {
                            redValue = it
                            newColor = Color(it, newColor.green, newColor.blue)
                        })
                }



                Row {
                    Text(
                        text = readebleColorCount(greenValue),
                        style = Typography.bodySmall
                    )

                    Slider(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        value = greenValue,
                        onValueChange = {
                            greenValue = it
                            newColor = Color(newColor.red, it, newColor.blue)
                        })
                }


                Row {
                    Text(
                        text = readebleColorCount(blueValue),
                        style = Typography.bodySmall
                    )
                    Slider(
                        modifier = Modifier
                            .fillMaxWidth(0.8f),
                        value = blueValue,
                        onValueChange = {
                            blueValue = it
                            newColor = Color(newColor.red, newColor.green, it)
                        })
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ){
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        onClick = {
                            drawColor.value = newColor
                            mainViewModel.recentColors += newColor
                            blendMode.value = newBlend

                            println("gewgqwegafqerqer")

                            println(blendMode.value)

                            isOpen.value = false
                        }
                    ) {
                        Text(text = "Сохранить", color = Color.Green)
                    }

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent
                        ),
                        onClick = {
                            isOpen.value = false
                        }
                    ) {
                        Text(text = "Отмена", color = Color.Red)
                    }
                }

            }
        }
    }
}