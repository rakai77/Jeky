package com.example.jeky.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jeky.presentation.navigation.Route
import com.example.jeky.presentation.screen.home.HomeScreen
import com.example.jeky.presentation.screen.login.LoginScreen
import com.example.jeky.presentation.screen.register.RegisterScreen
import com.example.jeky.presentation.theme.JekyTheme
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.rememberBottomSheetNavigator

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { false }
        }
        setContent {
            JekyTheme {
                JekyApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@Composable
fun JekyApp() {
    val bottomSheerNavigator = rememberBottomSheetNavigator()
    val navController = rememberNavController(bottomSheerNavigator)
    NavHost(navController = navController, startDestination = Route.Login.route) {
        composable(
            route = Route.Login.route
        ) {
            LoginScreen(
                onNavigateToRegister = {
                    navController.navigate(Route.Register.route)
                },
                onNavigateToHome = {
                    navController.navigate(Route.Home.route)
                }
            )
        }

        composable(
            route = Route.Register.route
        ) {
            RegisterScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Route.Home.route)
                }
            )
        }

        composable(
            route = Route.Home.route
        ) {
            HomeScreen()
        }
    }
}