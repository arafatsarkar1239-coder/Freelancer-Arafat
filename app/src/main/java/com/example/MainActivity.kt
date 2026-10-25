package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.ui.FreelancerArafatApp
import com.example.ui.theme.CharcoalBlack
import com.example.ui.theme.FreelancerArafatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FreelancerArafatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = CharcoalBlack
                ) {
                    FreelancerArafatApp()
                }
            }
        }
    }
}
