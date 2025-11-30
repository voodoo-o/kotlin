package com.example.alistwithdetails

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.navigation.compose.rememberNavController
import com.example.alistwithdetails.ui.navigation.AppNavigation
import com.example.alistwithdetails.ui.navigation.BottomNavigationBar
import com.example.alistwithdetails.ui.theme.AListWithDetailsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AListWithDetailsTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController = navController) }
                ) { innerPadding ->
                    AppNavigation(navController = navController, innerPadding = innerPadding)
                }
            }
        }
    }
}
