package com.tylerdev.lekkerweather.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.tylerdev.lekkerweather.R
import com.tylerdev.lekkerweather.presentation.ui.theme.DarkBlue
import com.tylerdev.lekkerweather.presentation.ui.theme.DeepBlue
import com.tylerdev.lekkerweather.presentation.ui.theme.LekkerWeatherTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry activity for the app.
 *
 * Requests location permissions on launch, then loads weather via [WeatherViewModel]. Hosts the
 * Compose UI with [WeatherCard] and a loading indicator driven by [WeatherState.isLoading].
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
        }
        permissionLauncher.launch(arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
        setContent {
            LekkerWeatherTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(DarkBlue)
                ) {
                    Column(modifier = Modifier.fillMaxSize().padding(top = 32.dp)) {
                        WeatherCard(
                            state = viewModel.state,
                            backgroundColor = DeepBlue
                        )
                    }
                    if (viewModel.state.isLoading) {
                        val composition by rememberLottieComposition(
                            LottieCompositionSpec.RawRes(R.raw.circle_loader)
                        )
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier
                                .size(500.dp)
                                .align(Alignment.Center)
                                .graphicsLayer {
                                    colorFilter = ColorFilter.tint(Color.White)
                                }
                        )
                    }
                }
            }
        }
    }
}