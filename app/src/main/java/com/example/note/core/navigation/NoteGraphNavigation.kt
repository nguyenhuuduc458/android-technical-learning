package com.example.note.core.navigation

import androidx.navigation.NavHostController
import kotlinx.serialization.Serializable

// Account route
@Serializable
object LoginRoute

@Serializable
object RegisterRoute

@Serializable
object NoteRoute

@Serializable
data class AddEditNoteRoute(
    val noteId: Int = -1,
)

@Serializable
object StartRoute

class NoteGraphNavigationActions(
    private val navController: NavHostController,
) {
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
        navController.navigateUp()
    }

    fun navigateToAddEditScreen(noteId: Int = -1) {
        navController.navigate(route = AddEditNoteRoute(noteId))
    }
}
