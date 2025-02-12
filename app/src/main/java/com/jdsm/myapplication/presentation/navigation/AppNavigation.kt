package com.jdsm.myapplication.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.jdsm.myapplication.presentation.login.LoginDestination
import com.jdsm.myapplication.presentation.login.loginScreen
import com.jdsm.myapplication.presentation.mainFlow.mainNavigationGraph
import com.jdsm.myapplication.presentation.mainFlow.navigateToMainGraph

@Composable
fun AppNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = LoginDestination,
        modifier = modifier
    ){
        loginScreen(
            onLogIn = {
                navController.navigateToMainGraph(
                    navOptions = NavOptions.Builder().setPopUpTo<LoginDestination>(
                        inclusive = true
                    ).build()
                )
            }
        )

        mainNavigationGraph(
            onLogOutClick = {
                navController.navigate(LoginDestination) {
                    popUpTo(0)
                }
            }
        )
    }
}
