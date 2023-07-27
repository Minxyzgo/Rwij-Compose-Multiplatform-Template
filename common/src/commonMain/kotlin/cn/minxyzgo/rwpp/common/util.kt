package cn.minxyzgo.rwpp.common

import java.io.File

fun releaseLib() {
    ClassLoader.getSystemClassLoader().getResourceAsStream("game-lib.jar")!!.use {
        val jarFile = File("game-lib.jar")
        if (!jarFile.exists()) {
            jarFile.parentFile.mkdirs()
            jarFile.createNewFile()
        }

        jarFile.writeBytes(it.readBytes())

    }
}