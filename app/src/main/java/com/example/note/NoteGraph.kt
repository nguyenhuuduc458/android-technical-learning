package com.example.note

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.note.account_module.presentation.login.LoginScreen
import com.example.note.account_module.presentation.register.RegisterScreen
import com.example.note.note_module.presentation.note.NoteScreen

@Composable
fun NoteGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: LoginRoute = LoginRoute,
    navActions: NoteGraphNavigationActions = remember(navController) {
        NoteGraphNavigationActions(navController)
    }
) {
    val currentNavBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentRoute: Any = currentNavBackStackEntry?.destination?.route ?: startDestination

    NavHost(navController = navController, startDestination = startDestination) {
        composable<LoginRoute> {
            LoginScreen(
                onLoginSuccess = { navActions.navigationToNote() },
                onSignUp = { navActions.navigateToRegister() }
            )
        }
        composable<RegisterRoute> {
            RegisterScreen(
                onSignIn = { navActions.navigateToLogin() }
            )
        }
        composable<NoteRoute> {
            NoteScreen()
        }
    }
}