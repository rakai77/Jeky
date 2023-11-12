package com.example.jeky.presentation

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.jeky.domain.model.EmptyStateModel
import com.example.jeky.presentation.navigation.Route
import com.example.jeky.presentation.screen.error.ErrorScreen
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
import com.google.gson.Gson

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { !viewModel.isSplashFinished.value }
        }
        setContent {
            JekyTheme {
                JekyApp(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class, ExperimentalMaterialApi::class)
@Composable
fun JekyApp(viewModel: MainViewModel) {
    val sheetState = rememberModalBottomSheetState(
        ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val bottomSheerNavigator = remember {BottomSheetNavigator(sheetState)}
    val navController = rememberNavController(bottomSheerNavigator)

    val isUserLoggedIn by viewModel.isUserLoggedIn.collectAsState()

    ModalBottomSheetLayout(
        bottomSheetNavigator = bottomSheerNavigator
    ) {
       isUserLoggedIn?.let {isLoggedIn ->
           NavHost(navController = navController, startDestination = if (isLoggedIn) Route.Home.route else Route.Login.route) {
               composable(
                   route = Route.Login.route
               ) {
                   val viewModel: LoginViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = LoginViewModel.Factory)
                   LoginScreen(
                       viewModel = viewModel,
                       onNavigateToHome = {
                           navController.navigate(Route.Home.route)
                       },
                       onNavigateToRegister = {
                           navController.navigate(Route.Register.route)
                       },
                       onLoginError = {
                           val json = Uri.encode(Gson().toJson(it))
                           navController.navigate("${Route.Error.route}/$json")
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
                       },
                       onRegisterError = {
                           val json = Uri.encode(Gson().toJson(it))
                           navController.navigate("${Route.Error.route}/$json")
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
               ) { backStackEntry ->
                   val isToGetPickupLocation = backStackEntry.arguments?.getBoolean("isToGetPickupLocation") ?: true
                   PickLocationBottomSheet(
                       isToGetPickupLocation,
                       onClose = {
                           navController.popBackStack()
                       }
                   )
               }

               bottomSheet(
                   route = "${Route.Error.route}/{empty-params}",
                   arguments = listOf(
                       navArgument("empty-params") { type = NavType.StringType }
                   )
               ) { backStackEntry ->
                   val emptyParams = backStackEntry.arguments?.getString("empty-params")
                   ErrorScreen(
                       Gson().fromJson(emptyParams, EmptyStateModel::class.java)
                   ) {
                       navController.popBackStack()
                   }
               }
           }
       }
    }
}