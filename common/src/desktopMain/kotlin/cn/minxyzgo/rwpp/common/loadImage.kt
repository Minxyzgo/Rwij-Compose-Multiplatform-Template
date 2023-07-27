package cn.minxyzgo.rwpp.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource

@Composable
actual fun loadImage(res: String): Painter = painterResource("drawable/$res")