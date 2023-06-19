package com.ntk.testapplicationforunisoldev.views.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ntk.testapplicationforunisoldev.ui.theme.AddictAppColor
import com.ntk.testapplicationforunisoldev.ui.theme.GrayDark
import com.ntk.testapplicationforunisoldev.ui.theme.Typography
import com.ntk.testapplicationforunisoldev.viewModels.MainViewModel

@Composable
fun RecentColorDialogView(
    drawColor: MutableState<Color>,
    mainViewModel: MainViewModel,
    isOpen: MutableState<Boolean>
){

    @Composable
    fun RecentColorItem(color: Color, drawColor: MutableState<Color>){
        Column(
            modifier = Modifier
                .size(50.dp)
                .background(color)
                .clickable {
                    drawColor.value = color
                    isOpen.value = false
                }
        ) {}
    }

    LazyRow(){
        items(mainViewModel.recentColors.reversed()){
            RecentColorItem(color = it, drawColor = drawColor)

            Spacer(modifier = Modifier.width(4.dp))
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ColorPickerDialog(
    isOpen: MutableState<Boolean>,
    drawColor: MutableState<Color>,
    mainViewModel: MainViewModel,
) {

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
        var redValue by remember { mutableStateOf(drawColor.value.red) }
        var greenValue by remember { mutableStateOf(drawColor.value.green) }
        var blueValue by remember { mutableStateOf(drawColor.value.blue) }

        var newColor by remember {
            mutableStateOf(Color(drawColor.value.red, drawColor.value.green, drawColor.value.blue))
        }

        Box(modifier = Modifier
            .background(GrayDark)
            .fillMaxSize()
        ) {



            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
            ){
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .background(newColor)
                    ) {}

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(text = "Цвета в приложении:", style = Typography.bodySmall)

                        RecentColorDialogView(drawColor, mainViewModel, isOpen)
                    }

                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter),
                horizontalAlignment = Alignment.CenterHorizontally
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