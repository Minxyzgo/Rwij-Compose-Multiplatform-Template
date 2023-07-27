package cn.minxyzgo.rwpp.common

import androidx.compose.runtime.Composable
import com.corrodinggames.rts.java.Main

actual fun getPlatformName(): String {
    return "Desktop"
}


actual fun startGame() {
    Main.main(emptyArray())
}