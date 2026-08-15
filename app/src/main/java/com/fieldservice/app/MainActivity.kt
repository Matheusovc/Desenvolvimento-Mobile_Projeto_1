package com.fieldservice.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.fieldservice.app.presentation.navigation.FieldServiceApp
import com.fieldservice.app.ui.theme.FieldServiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FieldServiceTheme {
                FieldServiceApp()
            }
        }
    }
}
