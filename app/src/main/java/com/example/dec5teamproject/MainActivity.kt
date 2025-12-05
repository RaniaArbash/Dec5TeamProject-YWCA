package com.example.dec5teamproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dec5teamproject.UILayer.PopularDestinationsScreen
import com.example.dec5teamproject.model.Country
import com.example.dec5teamproject.ui.theme.Dec5TeamProjectTheme
import com.example.dec5teamproject.viewmodel.CountriesViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Dec5TeamProjectTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val countriesVm: CountriesViewModel = viewModel()

                    PopularDestinationsScreen(
                        viewModel = countriesVm,
                        onCountrySelected = { country ->
                            //  just log selection
                            println("Selected: ${country.name}")
                        }
                    )
                }
            }
        }
    }
}


