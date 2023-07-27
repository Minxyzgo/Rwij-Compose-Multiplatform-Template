package cn.minxyzgo.rwpp.common

import com.github.minxyzgo.rwij.setFunction

typealias Game = com.corrodinggames.rts.game.i
lateinit var startGameActivity: () -> Unit

actual fun getPlatformName(): String {
    return "Android"
}

actual fun startGame() {
    startGameActivity()
}