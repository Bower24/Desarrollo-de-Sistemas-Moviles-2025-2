package com.example.flightsearch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flightsearch.ui.FlightScreen
import com.example.flightsearch.ui.FlightViewModelFactory
import com.example.flightsearch.ui.theme.FlightSearchTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlightSearchTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val appContainer = (application as FlightSearchApplication).container
                    FlightScreen(
                        viewModel = viewModel(
                            factory = FlightViewModelFactory(
                                appContainer.database,
                                appContainer.userPreferencesRepository
                            )
                        )
                    )
                }
            }
        }
    }
}