package com.example.alistwithdetails.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.alistwithdetails.ui.screens.ProfileScreen
import com.example.alistwithdetails.ui.screens.RepoDetailsScreen
import com.example.alistwithdetails.ui.screens.ReposListScreen
import com.example.alistwithdetails.ui.screens.SearchScreen

sealed class Screen(val route: String) {
    object ReposList : Screen("repos_list")
    object Search : Screen("search")
    object Profile : Screen("profile")
    object RepoDetails : Screen("repo_details/{owner}/{repoName}") {
        fun withArgs(owner: String, repoName: String): String {
            return "repo_details/$owner/$repoName"
        }
    }
}

data class BottomNavItem(
    val label: String,
    val icon: ImageVector,
    val route: String
)

@Composable
fun AppNavigation(navController: NavHostController, innerPadding: PaddingValues) {
    NavHost(navController, startDestination = Screen.ReposList.route, modifier = Modifier.padding(innerPadding)) {
        composable(Screen.ReposList.route) { ReposListScreen(navController) }
        composable(Screen.Search.route) { SearchScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
        composable(
            route = Screen.RepoDetails.route,
            arguments = listOf(
                navArgument("owner") { type = NavType.StringType },
                navArgument("repoName") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val owner = backStackEntry.arguments?.getString("owner") ?: ""
            val repoName = backStackEntry.arguments?.getString("repoName") ?: ""
            RepoDetailsScreen(owner = owner, repoName = repoName, navController = navController)
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem("Repositories", Icons.Default.List, Screen.ReposList.route),
        BottomNavItem("Search", Icons.Default.Search, Screen.Search.route),
        BottomNavItem("Profile", Icons.Default.AccountCircle, Screen.Profile.route)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val shouldShowBottomBar = items.any { it.route == currentDestination?.route }

    if (shouldShowBottomBar) {
        NavigationBar {
            items.forEach { item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.label) },
                    label = { Text(item.label) },
                    selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                    onClick = {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    }
}
