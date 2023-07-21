package com.example.MoviesAPi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.MoviesAPi.ui.screens.MainScreen
import com.example.MoviesAPi.ui.theme.MoviesAPi

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HitemaMovieTheme {
                MainScreen()
            }
        }
    }
}