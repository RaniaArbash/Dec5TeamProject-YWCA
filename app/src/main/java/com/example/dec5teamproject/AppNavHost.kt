package com.example.dec5teamproject


import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import androidx.hilt.navigation.compose.hiltViewModel // if using Hilt; else manual
import com.example.dec5teamproject.UILayer.PopularDestinationsScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()
    //val sharedVm = androidx.lifecycle.viewmodel.compose.viewModel<SharedTravelViewModel>()
    //val countriesVm = androidx.lifecycle.viewmodel.compose.viewModel<CountriesViewModel>()

    NavHost(navController, startDestination = "popular") {
        composable("popular") {
            //PopularDestinationsScreen(


              //  viewModel = countriesVm,
               // onCountrySelected = { country ->
                //    sharedVm.setSelectedCountry(country)
                 //   navController.navigate("details")
               // }
            //)
        }
        // Example: details screen can read sharedVm.selectedCountry
        composable("details") { /* TODO: Screen 2-5 integrate later */ }
    }
}
