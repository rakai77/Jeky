package com.example.jeky.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jeky.presentation.navigation.Route
import com.example.jeky.presentation.screen.home.HomeScreen
import com.example.jeky.presentation.screen.login.LoginScreen
import com.example.jeky.presentation.screen.login.LoginViewModel
import com.example.jeky.presentation.screen.picklocation.PickLocationBottomSheet
import com.example.jeky.presentation.screen.register.RegisterScreen
import com.example.jeky.presentation.screen.register.RegisterViewModel
import com.example.jeky.presentation.theme.JekyTheme
import com.google.accompanist.navigation.material.BottomSheetNavigator
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.google.accompanist.navigation.material.ModalBottomSheetLayout
import com.google.accompanist.navigation.material.bottomSheet

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

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
@Composable
fun JekyApp() {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val bottomSheerNavigator = remember {BottomSheetNavigator(sheetState)}
    val navController = rememberNavController(bottomSheerNavigator)

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheerNavigator
    ) {
        NavHost(navController = navController, startDestination = Route.Login.route) {
            composable(
                route = Route.Login.route
            ) {
                val viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = LoginViewModel.Factory)
                LoginScreen(
                    viewModel = viewModel,
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
                val viewModel: RegisterViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = RegisterViewModel.Factory)
                RegisterScreen(
                    viewModel = viewModel,
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
                HomeScreen(
                    onEditButtonClick = {
                        navController.navigate("${Route.PickLocationBottomSheet.route}/true")
                    }
                )
            }

            bottomSheet(
                route = "${Route.PickLocationBottomSheet.route}/{isToGetPickupLocation}",
                arguments = listOf(
                    navArgument("isToGetPickupLocation") { type = NavType.BoolType }
                )
            ) { backstackEntry ->
                val isToGetPickupLocation = backstackEntry.arguments?.getBoolean("isToGetPickupLocation") ?: true
                PickLocationBottomSheet(
                    isToGetPickupLocation = isToGetPickupLocation,
                    onClose = {
                        navController.popBackStack()
                    }
                )
            }
        }
    }
}