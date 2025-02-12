package com.jdsm.myapplication.presentation.login

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object LoginDestination

fun NavGraphBuilder.loginScreen(
    onLogIn: () -> Unit
) {
    composable<LoginDestination> {
        LoginRoute(
            onLogIn = onLogIn
        )
    }
}