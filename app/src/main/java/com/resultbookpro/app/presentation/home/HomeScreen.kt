package com.resultbookpro.app.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.resultbookpro.app.presentation.analytics.AnalyticsScreen
import com.resultbookpro.app.presentation.marks.list.MarksListScreen
import com.resultbookpro.app.presentation.profile.ProfileScreen
import com.resultbookpro.app.presentation.upcoming.UpcomingScreen
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Upcoming : Screen("upcoming", Icons.Filled.DateRange, "Upcoming")
    object Analytics : Screen("analytics", Icons.Filled.DateRange, "Analytics")
    object Marks : Screen("marks", Icons.Filled.List, "Marks")
    object Profile : Screen("profile", Icons.Filled.Person, "Profile")
}

@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val items = listOf(Screen.Upcoming, Screen.Analytics, Screen.Marks, Screen.Profile)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = null) },
                        label = { Text(screen.title) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
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
    ) { innerPadding ->
        NavHost(
            navController,
            startDestination = Screen.Upcoming.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Screen.Upcoming.route) { UpcomingScreen(onAddReminderClicked = {}) }
            composable(Screen.Analytics.route) { AnalyticsScreen() }
            composable(Screen.Marks.route) { MarksListScreen(onAddMark = {}, onMarkSwiped = {}) }
            composable(Screen.Profile.route) { ProfileScreen(onEditProfile = {}, onLogout = {}) }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ResultBookProTheme {
        HomeScreen()
    }
}
