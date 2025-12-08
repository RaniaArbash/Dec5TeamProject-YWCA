package com.example.dec5teamproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dec5teamproject.ui.theme.Dec5TeamProjectTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            Dec5TeamProjectTheme {
                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "events_screen",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        // Add your screens here
                        composable("events_screen") {
                            val viewModel: EventsViewModel = viewModel()
                            EventsScreen(viewModel)
                        }
                    }
                }
            }
        }
    }
}
