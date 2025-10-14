package com.example.todonotesapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.todonotesapp.ui.theme.screens.MainScreen
import com.example.todonotesapp.ui.theme.screens.NoteDetailScreen
import com.example.todonotesapp.viewmodel.NoteViewModel


@Composable
fun Navigation(
    navController: NavHostController,
    viewModel: NoteViewModel
) {
    NavHost(
        navController,
        startDestination = "main_screen",
    ) {
        composable("main_screen") {
            MainScreen(viewModel = viewModel, navController = navController)
        }

        composable(
            route = "note_detail/{noteId}",
            arguments = listOf(navArgument("noteId") { type = NavType.LongType })
        ) { backStackEntry ->
            val noteId = backStackEntry.arguments?.getLong("noteId") ?: 0L
            NoteDetailScreen(
                viewModel = viewModel,
                noteId = noteId,
                onBack = { navController.popBackStack() })
        }
    }
}

