package com.ntk.testapplicationforunisoldev.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ntk.testapplicationforunisoldev.ui.theme.*

@Composable
fun CustomBottomNavigationView(navHostController: NavHostController) {

    var selectMenuPoint by remember {
        mutableStateOf(0)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp)
            .shadow(ambientColor = MainAppColor, elevation = 12.dp, spotColor = MainAppColor),
        colors = CardDefaults.cardColors(
            containerColor = GrayLight,
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(GrayLight)
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            for((index, item) in BottomNavList.withIndex()){
                Column(
                    modifier = Modifier
                        .clickable {
                            if(selectMenuPoint != index){
                                selectMenuPoint = index
                                navHostController.navigate(item.obj.route)
                            }
                        }
                        .width(100.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = if(selectMenuPoint == index) MainAppColor else AddictAppColor
                    )

                    Text(
                        text = item.title,
                        style = Typography.bodySmall,
                        color = if(selectMenuPoint == index) Color.White else GrayMenuPoint
                    )
                }
            }
        }
    }
}