package com.rudra.carelog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.rudra.carelog.navigation.AppNavHost
import com.rudra.carelog.ui.theme.CareLogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CareLogTheme {
                AppNavHost()
            }
        }
    }
}
