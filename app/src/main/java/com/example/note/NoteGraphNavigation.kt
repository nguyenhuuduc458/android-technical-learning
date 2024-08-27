package com.example.note

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

// Account route
@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
object NoteRoute

class NoteGraphNavigationActions(private val navController: NavHostController) {
    fun navigateToLogin() {
        navController.navigate(route = LoginRoute) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun navigateToRegister() {
        navController.navigate(route = RegisterRoute) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun navigationToNote() {
        navController.navigate(route = NoteRoute) {
            popUpTo(navController.graph.startDestinationId) {
                inclusive = true
            }
            launchSingleTop = true
        }
    }

    fun onBackPressed() {
        navController.popBackStack()
    }
}