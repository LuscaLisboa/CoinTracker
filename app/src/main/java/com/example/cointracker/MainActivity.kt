package com.example.cointracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cointracker.screen.detailedCoinScreen.DetailedCoinScreen
import com.example.cointracker.screen.mainScreen.MainScreen
import com.example.cointracker.ui.theme.CoinTrackerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinTrackerTheme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "MainScreen"){

                    composable(route = "MainScreen"){
                        MainScreen(navController)
                    }

                    composable(route = "DetailedCoinScreen/{id}"){
                        backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")
                        DetailedCoinScreen(navController, id = id)
                    }
                }
            }
        }
    }
}



