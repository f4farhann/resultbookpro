package com.scortrax.app.presentation.home

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
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
import com.scortrax.app.R
import com.scortrax.app.presentation.analytics.AnalyticsScreen
import com.scortrax.app.presentation.marks.list.MarksListScreen
import com.scortrax.app.presentation.profile.ProfileScreen
import com.scortrax.app.presentation.profile.ProfileViewModel
import com.scortrax.app.presentation.upcoming.UpcomingScreen
import com.scortrax.app.presentation.common.theme.PrimaryBlue
import com.scortrax.app.presentation.common.theme.ScortraxTheme
import com.scortrax.app.presentation.common.theme.White
import androidx.lifecycle.viewmodel.compose.viewModel
import com.scortrax.app.presentation.marks.edit.EditAcademicRecordViewModel
import kotlinx.coroutines.launch

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
    onEditRecord: () -> Unit,
    onLogout: () -> Unit,
    profileViewModel: ProfileViewModel = viewModel(),
    editAcademicRecordViewModel: EditAcademicRecordViewModel = viewModel()
) {
    val navController = rememberNavController()
    val items = listOf(ScreenIcon.Upcoming, ScreenIcon.Analytics, ScreenIcon.Marks, ScreenIcon.Profile)
    
    val pagerState = rememberPagerState(pageCount = { items.size })
    val coroutineScope = rememberCoroutineScope()

    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    val currentRoute = currentDestination?.route

    val profileState by profileViewModel.state.collectAsState()
    val RecordeState by editAcademicRecordViewModel.state.collectAsState()

    val topBarTitle = if (currentRoute == "home") {
        when (items[pagerState.currentPage]) {
            ScreenIcon.Analytics -> "Analytics"
            ScreenIcon.Marks -> "Marks"
            ScreenIcon.Profile -> "Profile"
            else -> "Scortrax"
        }
    } else {
        when (currentRoute) {
            ScreenIcon.Analytics.route -> "Analytics"
            ScreenIcon.Marks.route -> "Marks"
            ScreenIcon.Profile.route -> "Profile"
            else -> "Scortrax"
        }
    }

    Scaffold(
        topBar = {
            if (currentRoute?.startsWith("edit_record") != true) {
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
                    ),
                )
            }
        },
        bottomBar = {
            if (currentRoute?.startsWith("edit_record") != true) {
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
                            items.forEachIndexed { index, screen ->
                                val selected = if (currentRoute == "home") {
                                    pagerState.currentPage == index
                                } else {
                                    currentDestination?.hierarchy?.any { it.route == screen.route } == true
                                }
                                
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
                                            if (currentRoute != "home") {
                                                navController.navigate("home") {
                                                    popUpTo(navController.graph.findStartDestination().id) {
                                                        saveState = true
                                                    }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                            coroutineScope.launch {
                                                pagerState.animateScrollToPage(index)
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
        },
        contentWindowInsets = WindowInsets(0.dp)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = if (currentRoute?.startsWith("edit_record") == true) 0.dp else innerPadding.calculateTopPadding())
        ) {
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.fillMaxSize(),
                enterTransition = { 
                    fadeIn(animationSpec = tween(300))
                },
                exitTransition = { 
                    fadeOut(animationSpec = tween(300))
                }
            ) {
                composable("home") {
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier.fillMaxSize(),
                        beyondViewportPageCount = 3
                    ) { page ->
                        when (items[page]) {
                            ScreenIcon.Upcoming -> UpcomingScreen()
                            ScreenIcon.Analytics -> AnalyticsScreen()
                            ScreenIcon.Marks -> {
                                MarksListScreen(
                                    studyLevelFromProfile = profileState.studyLevel,
                                    onEditRecord = onEditRecord,
                                )
                            }
                            ScreenIcon.Profile -> {
                                ProfileScreen(
                                    onEditProfile = onEditProfile,
                                    onLogout = onLogout,
                                    viewModel = profileViewModel
                                )
                            }
                        }
                    }
                }
//                composable(
//                    route = "edit_record/{year}",
//                    arguments = listOf(navArgument("year") { type = NavType.StringType })
//                ) { backStackEntry ->
//                    val year = backStackEntry.arguments?.getString("year") ?: ""
//                    EditAcademicRecordScreen(
//                        onBack = { navController.popBackStack() },
//                        onSave = { navController.popBackStack() },
//                        editAcademicRecordViewModel = editAcademicRecordViewModel
//                    )
//                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    ScortraxTheme {
        HomeScreen(onEditProfile = {}, onEditRecord = {}, onLogout = {})
    }
}
