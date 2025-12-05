package com.example.dec5teamproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.dec5teamproject.UILayer.PopularDestinationsScreen
import com.example.dec5teamproject.ui.theme.Dec5TeamProjectTheme
import com.example.exchangeapi.ExchangeRateApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dec5TeamProjectTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PopularDestinationsScreen()
                }
            }
        }
    }
}

