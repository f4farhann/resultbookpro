package com.resultbookpro.app.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
import com.resultbookpro.app.presentation.common.theme.PrimaryBlue
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme
import com.resultbookpro.app.presentation.common.theme.White

sealed class Screen(val route: String, val icon: ImageVector, val title: String) {
    object Upcoming : Screen("upcoming", Icons.Filled.DateRange, "Upcoming")
    object Analytics : Screen("analytics", Icons.Filled.DateRange, "Analytics")
    object Marks : Screen("marks", Icons.Filled.List, "Marks")
    object Profile : Screen("profile", Icons.Filled.Person, "Profile")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val navController = rememberNavController()
    val items = listOf(Screen.Upcoming, Screen.Analytics, Screen.Marks, Screen.Profile)
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = currentDestination?.route

    val topBarTitle = if (currentRoute == Screen.Profile.route) "My Account" else "ResultBookPro"

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = topBarTitle,
                        fontWeight = FontWeight.Bold
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = White,
                    actionIconContentColor = White,
                    navigationIconContentColor = White
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = PrimaryBlue,
                contentColor = White
            ) {
                items.forEach { screen ->
                    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                    NavigationBarItem(
                        icon = { 
                            Icon(
                                imageVector = screen.icon, 
                                contentDescription = null
                            ) 
                        },
                        label = { Text(screen.title) },
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PrimaryBlue,
                            selectedTextColor = White,
                            unselectedIconColor = White.copy(alpha = 0.6f),
                            unselectedTextColor = White.copy(alpha = 0.6f),
                            indicatorColor = White
                        )
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
