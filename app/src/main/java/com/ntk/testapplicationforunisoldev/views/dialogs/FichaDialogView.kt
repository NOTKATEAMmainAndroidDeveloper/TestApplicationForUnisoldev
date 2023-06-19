package com.ntk.testapplicationforunisoldev.views.dialogs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ntk.testapplicationforunisoldev.ui.theme.Typography
import kotlinx.coroutines.*

var StoryOfLife = "То, что кошки животные наиумнейшие – вряд ли кто поспорит. Расскажу вам еще одну историю про кота, которая в который раз подтвердит сей постулат.\n" +
        "Девушка у своего молодого человека. Парень на кухне, она - его компьютером. Кричит ему:\n" +
        "- Слушай, твой кот RESET нажал, компьютер пароль требует.\n" +
        "- Погладь кота!\n" +
        "- Я говорю, компьютер не включается, а ты все со своим котом носишься! – начинает заводиться девушка.\n" +
        "\n" +
        "- А я говорю, пароль poglad_kota. Мой кот всегда RESET нажимает, когда хочет, чтобы его погладили!"

@OptIn(DelicateCoroutinesApi::class)
fun fichaWork(text: MutableState<String>){
    GlobalScope.launch {
        println("Gerjwernsejgnwejrnnernrewg")

        for(chr in StoryOfLife){
            if(chr != ' ')delay(75L)
            text.value += chr
        }
    }.start()
}

@Composable
fun FichaDialogView() {
    val textText = remember {
        mutableStateOf("")
    }

    if(textText.value.isEmpty())fichaWork(textText)

    LazyColumn(){
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item {
            Text(textText.value, style = Typography.bodySmall, color = Color.White)
        }
    }
}