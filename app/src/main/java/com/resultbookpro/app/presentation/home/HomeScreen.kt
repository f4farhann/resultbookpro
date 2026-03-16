package com.resultbookpro.app.presentation.home

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.resultbookpro.app.R
import com.resultbookpro.app.presentation.analytics.AnalyticsScreen
import com.resultbookpro.app.presentation.marks.list.MarksListScreen
import com.resultbookpro.app.presentation.profile.ProfileScreen
import com.resultbookpro.app.presentation.profile.ProfileViewModel
import com.resultbookpro.app.presentation.upcoming.UpcomingScreen
import com.resultbookpro.app.presentation.common.theme.PrimaryBlue
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme
import com.resultbookpro.app.presentation.common.theme.White
import androidx.lifecycle.viewmodel.compose.viewModel

sealed class ScreenIcon(val route: String, val title: String) {
    @Composable
    abstract fun icon(): Painter

    object Upcoming : ScreenIcon("upcoming", "Upcoming") {
        @Composable
        override fun icon() = rememberVectorPainter(Icons.Default.DateRange)
    }
    object Analytics : ScreenIcon("analytics", "Analytics") {
        @Composable
        override fun icon() = painterResource(id = R.drawable.outline_finance_25)
    }
    object Marks : ScreenIcon("marks", "Marks") {
        @Composable
        override fun icon() = rememberVectorPainter(Icons.AutoMirrored.Filled.List)
    }
    object Profile : ScreenIcon("profile", "Profile") {
        @Composable
        override fun icon() = rememberVectorPainter(Icons.Default.Person)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onEditProfile: () -> Unit,
    onLogout: () -> Unit,
    profileViewModel: ProfileViewModel = viewModel()
) {
    val navController = rememberNavController()
    val items = listOf(ScreenIcon.Upcoming, ScreenIcon.Analytics, ScreenIcon.Marks, ScreenIcon.Profile)
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    val currentRoute = currentDestination?.route

    val profileState by profileViewModel.state.collectAsState()

    val topBarTitle = if (currentRoute == ScreenIcon.Profile.route) "My Account" else "ResultBookPro"

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
                                painter = screen.icon(),
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
            startDestination = ScreenIcon.Upcoming.route,
            Modifier.padding(innerPadding)
        ) {
            composable(ScreenIcon.Upcoming.route) { UpcomingScreen(onAddReminderClicked = {}) }
            composable(ScreenIcon.Analytics.route) { AnalyticsScreen() }
            composable(ScreenIcon.Marks.route) { 
                MarksListScreen(
                    studyLevelFromProfile = profileState.studyLevel,
                ) 
            }
            composable(ScreenIcon.Profile.route) { 
                ProfileScreen(
                    onEditProfile = onEditProfile, 
                    onLogout = onLogout,
                    viewModel = profileViewModel
                ) 
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ResultBookProTheme {
        HomeScreen(onEditProfile = {}, onLogout = {})
    }
}
