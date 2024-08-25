package com.example.tic_tac_toeapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "SplashScreen") {
        composable(route = "SplashScreen") {
            SplashScreen(navController)
        }
        composable(route = "TicTacToeScreen") {
            TicTacToeScreen()
        }
    }
}