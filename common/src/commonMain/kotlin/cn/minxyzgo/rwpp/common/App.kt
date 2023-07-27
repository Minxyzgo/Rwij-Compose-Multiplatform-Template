package cn.minxyzgo.rwpp.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.github.minxyzgo.rwij.Builder
import com.github.minxyzgo.rwij.Libs

@Composable
fun ShowMenu() {
    //releaseLib()
    MaterialTheme {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0, 51, 0)),
            contentAlignment = Alignment.TopCenter
        ) {
            //Image(painter = loadImage("menu.png"), contentDescription = "background", modifier = Modifier.fillMaxSize())
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.size(100.dp))
                Image(painter = loadImage("title.png"), "title", modifier = Modifier.padding(5.dp))

                MenuButton("Start Game", Icons.Filled.Person) {
                    Thread {
                        startGame()
                    }.start()
                }
            }
        }
    }
}

@Composable
fun MenuButton(content: String, icon: ImageVector? = null, onClick: () -> Unit) {

    Spacer(modifier = Modifier.size(20.dp))

    val extendedFabIconPadding = 8.dp
    val extendedFabTextPadding = 8.dp
    var isPressed by remember { mutableStateOf(false) }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .requiredHeight(60.dp)
            .width(150.dp)
            .clip(CircleShape)
            .pointerInput(Unit) {
                detectTapGestures(onPress = {
                    isPressed = true
                    tryAwaitRelease()
                    isPressed = false
                }, onTap = { onClick() })
            }
    ) {
        val startPadding = if (icon == null) extendedFabTextPadding else extendedFabIconPadding
        Image(
            painter = loadImage(if(!isPressed) "rounded_glow_button.png" else "rounded_glow_highlight_button.png"),
            contentDescription = null,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxSize()
                .alpha(if(!isPressed) 0.85f else 1f)
        )

        Row(
            modifier = Modifier.padding(
                start = startPadding,
                end = extendedFabTextPadding
            ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(icon, null)
                Spacer(Modifier.width(extendedFabIconPadding))
            }
            Text(text = content, fontSize = 12.sp)
        }
    }
}