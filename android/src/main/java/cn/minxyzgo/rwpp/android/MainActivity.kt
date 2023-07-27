package cn.minxyzgo.rwpp.android

import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import cn.minxyzgo.rwpp.common.ShowMenu
import cn.minxyzgo.rwpp.common.startGameActivity


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        doProxy()

        startGameActivity = {
            startActivity(Intent(this, com.corrodinggames.rts.appFramework.MainMenuActivity::class.java))
        }

        setContent {
            MaterialTheme {
                ShowMenu()
            }
        }
    }
}