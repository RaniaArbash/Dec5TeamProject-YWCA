package com.example.dec5teamproject

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.dec5teamproject.Navigation.ScreenRoutes
import com.example.dec5teamproject.UILayer.PopularDestinationsScreen
import com.example.exchangeapi.ExchangeRateApp
import com.example.dec5teamproject.UILayer.TouristAttractionsScreen
import com.example.dec5teamproject.FunContentScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Travel Explorer App") }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentRoute == ScreenRoutes.POPULAR,
                    onClick = { navController.navigate(ScreenRoutes.POPULAR) },
                    icon = { Icon(Icons.Filled.Home, contentDescription = "Destinations") },
                    label = { Text("Destinations") }
                )
                NavigationBarItem(
                    selected = currentRoute == ScreenRoutes.CURRENCY,
                    onClick = { navController.navigate(ScreenRoutes.CURRENCY) },
                    icon = { Icon(Icons.Filled.AttachMoney, contentDescription = "Currency") },
                    label = { Text("Currency") }
                )
                NavigationBarItem(
                    selected = currentRoute == ScreenRoutes.ATTRACTIONS,
                    onClick = { navController.navigate(ScreenRoutes.ATTRACTIONS) },
                    icon = { Icon(Icons.Filled.Place, contentDescription = "Attractions") },
                    label = { Text("Attractions") }
                )
                NavigationBarItem(
                    selected = currentRoute == ScreenRoutes.EVENTS,
                    onClick = { navController.navigate(ScreenRoutes.EVENTS) },
                    icon = { Icon(Icons.Filled.Event, contentDescription = "Events") },
                    label = { Text("Events") }
                )
                NavigationBarItem(
                    selected = currentRoute == ScreenRoutes.FUN,
                    onClick = { navController.navigate(ScreenRoutes.FUN) },
                    icon = { Icon(Icons.Filled.Star, contentDescription = "Fun") },
                    label = { Text("Fun") }
                )
            }
        }
    ) { innerPadding ->
        AppNavHost(
            navController = navController,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.POPULAR,
        modifier = modifier
    ) {
        // Screen 1 – Popular Destinations
        composable(ScreenRoutes.POPULAR) {
            PopularDestinationsScreen(navController,modifier = modifier)
        }

        // Screen 2 – Currency Exchange
        composable(ScreenRoutes.CURRENCY) {
            ExchangeRateApp()
        }

        // Screen 3 – Tourist Attractions
        composable(ScreenRoutes.ATTRACTIONS) {
            TouristAttractionsScreen()
        }

        // Screen 4 – Events (placeholder for now)
        composable(ScreenRoutes.EVENTS) {
            Text(
                text = "Screen 4: Events Screen (To be implemented)",
                modifier = Modifier.padding(16.dp)
            )
        }

        // Screen 5 – Fun Content
        composable(ScreenRoutes.FUN) {
            FunContentScreen()
        }
    }
}
