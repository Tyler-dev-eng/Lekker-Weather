package com.tylerdev.lekkerweather.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
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

    @OptIn(ExperimentalMaterial3Api::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
            navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT),
        )
        permissionLauncher =
            registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions(),
            ) {
                viewModel.loadWeatherInfo()
            }
        permissionLauncher.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
            ),
        )
        setContent {
            LekkerWeatherTheme {
                PullToRefreshBox(
                    isRefreshing = viewModel.state.isLoading,
                    onRefresh = { viewModel.loadWeatherInfo() },
                    modifier =
                        Modifier
                            .fillMaxSize()
                            .background(DarkBlue),
                ) {
                    val state = viewModel.state
                    when {
                        state.error != null && !state.isLoading -> {
                            ErrorScreen(
                                message = state.error,
                                onRetry = { viewModel.loadWeatherInfo() },
                            )
                        }

                        else -> {
                            LazyColumn(
                                modifier = Modifier.fillMaxSize().padding(top = 32.dp),
                                contentPadding = WindowInsets.navigationBars.asPaddingValues(),
                            ) {
                                item {
                                    WeatherCard(
                                        state = state,
                                        backgroundColor = DeepBlue,
                                        locationName = state.locationName,
                                    )
                                }
                                item {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    WeatherForecast(state = state, modifier = Modifier)
                                }
                                item {
                                    Spacer(modifier = Modifier.height(16.dp))
                                    WeeklyForecast(state = state)
                                }
                            }
                        }
                    }
                    if (state.isLoading) {
                        val composition by rememberLottieComposition(
                            LottieCompositionSpec.RawRes(R.raw.circle_loader),
                        )
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            modifier =
                                Modifier
                                    .size(500.dp)
                                    .align(Alignment.Center)
                                    .graphicsLayer {
                                        colorFilter = ColorFilter.tint(Color.White)
                                    },
                        )
                    }
                }
            }
        }
    }
}
