package com.resultbookpro.app.presentation.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            // Floating navigation bar with more compact layout
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 0.dp)
                    .navigationBarsPadding()
            ) {
                Surface(
                    color = White.copy(alpha = 0.99f),
                    shape = RoundedCornerShape(32.dp),
                    shadowElevation = 8.dp,
                    tonalElevation = 2.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .padding(horizontal = 8.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        items.forEach { screen ->
                            val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true
                            
                            val animatedScale by animateFloatAsState(
                                targetValue = if (selected) 1.15f else 1.0f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                label = "iconScale"
                            )
                            
                            val animatedOffset by animateDpAsState(
                                targetValue = if (selected) (-2).dp else 0.dp,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                ),
                                label = "iconOffset"
                            )

                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxHeight()
                                    .padding(top = 2.dp, bottom = 4.dp)
                                    .clip(RoundedCornerShape(36.dp))
                                    .background(if (selected) PrimaryBlue.copy(alpha = 0.1f) else Color.Transparent)
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        if (currentRoute != screen.route) {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Box(
                                    modifier = Modifier.padding(top = 0.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    if (screen is ScreenIcon.Profile) {
                                        Box(
                                            modifier = Modifier
                                                .size(22.dp)
                                                .clip(CircleShape)
                                                .background(if (selected) PrimaryBlue else Color.Black)
                                                .graphicsLayer {
                                                    scaleX = animatedScale
                                                    scaleY = animatedScale
                                                    translationY = animatedOffset.toPx()
                                                },
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Icon(
                                                painter = screen.icon(),
                                                contentDescription = null,
                                                tint = White,
                                                modifier = Modifier.size(14.dp)
                                            )
                                        }
                                    } else {
                                        Icon(
                                            painter = screen.icon(),
                                            contentDescription = null,
                                            tint = if (selected) PrimaryBlue else Color.Black,
                                            modifier = Modifier
                                                .size(18.dp)
                                                .graphicsLayer {
                                                    scaleX = animatedScale
                                                    scaleY = animatedScale
                                                    translationY = animatedOffset.toPx()
                                                }
                                        )
                                    }
                                }
                                Text(
                                    text = screen.title,
                                    fontSize = 12.sp,
                                    fontWeight = if (selected) FontWeight.ExtraBold else FontWeight.SemiBold,
                                    color = if (selected) PrimaryBlue else Color.Black,
                                    modifier = Modifier.offset(y = 0.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        ) {
            NavHost(
                navController = navController,
                startDestination = ScreenIcon.Upcoming.route,
                modifier = Modifier.fillMaxSize(),
                enterTransition = { 
                    val initialIndex = items.indexOfFirst { it.route == initialState.destination.route }
                    val targetIndex = items.indexOfFirst { it.route == targetState.destination.route }
                    
                    if (targetIndex > initialIndex) {
                        slideInHorizontally(
                            initialOffsetX = { it }, 
                            animationSpec = tween(300, easing = LinearOutSlowInEasing)
                        ) + fadeIn(animationSpec = tween(300))
                    } else {
                        slideInHorizontally(
                            initialOffsetX = { -it }, 
                            animationSpec = tween(300, easing = LinearOutSlowInEasing)
                        ) + fadeIn(animationSpec = tween(300))
                    }
                },
                exitTransition = { 
                    val initialIndex = items.indexOfFirst { it.route == initialState.destination.route }
                    val targetIndex = items.indexOfFirst { it.route == targetState.destination.route }
                    
                    if (targetIndex > initialIndex) {
                        slideOutHorizontally(
                            targetOffsetX = { -it }, 
                            animationSpec = tween(300, easing = LinearOutSlowInEasing)
                        ) + fadeOut(animationSpec = tween(300))
                    } else {
                        slideOutHorizontally(
                            targetOffsetX = { it }, 
                            animationSpec = tween(300, easing = LinearOutSlowInEasing)
                        ) + fadeOut(animationSpec = tween(300))
                    }
                }
            ) {
                composable(ScreenIcon.Upcoming.route) { 
                    UpcomingScreen()
                }
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
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ResultBookProTheme {
        HomeScreen(onEditProfile = {}, onLogout = {})
    }
}
