package com.resultbookpro.app.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.resultbookpro.app.presentation.auth.login.LoginScreen
import com.resultbookpro.app.presentation.auth.login.LoginViewModel
import com.resultbookpro.app.presentation.auth.register.RegisterScreen
import com.resultbookpro.app.presentation.auth.register.RegisterViewModel
import com.resultbookpro.app.presentation.category.CategoryScreen
import com.resultbookpro.app.presentation.home.HomeScreen
import com.resultbookpro.app.presentation.profile.EditProfileScreen
import com.resultbookpro.app.presentation.profile.SetupProfileScreen
import com.resultbookpro.app.presentation.profile.SetupProfileViewModel

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController, 
        startDestination = ScreenRoutes.Category,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(500)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(500)
            )
        }
    ) {
        composable(ScreenRoutes.Category) {
            CategoryScreen(
                onCategorySelected = { _ ->
                    navController.navigate(ScreenRoutes.Login)
                }
            )
        }
        composable(ScreenRoutes.Login) {
            val viewModel: LoginViewModel = viewModel()
            val emailState by viewModel.emailState.collectAsState()
            val passwordState by viewModel.passwordState.collectAsState()

            LoginScreen(
                onLoginClick = { _, _ ->
                    navController.navigate(ScreenRoutes.Home) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                 },
                onRegisterClick = { navController.navigate(ScreenRoutes.Register) },
                emailState = emailState,
                passwordState = passwordState,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange
            )
        }
        composable(ScreenRoutes.Register) {
            val viewModel: RegisterViewModel = viewModel()
            val nameState by viewModel.nameState.collectAsState()
            val emailState by viewModel.emailState.collectAsState()
            val passwordState by viewModel.passwordState.collectAsState()

            RegisterScreen(
                onRegisterClick = { _, _, _ -> navController.navigate(ScreenRoutes.SetupProfile) },
                nameState = nameState,
                emailState = emailState,
                passwordState = passwordState,
                onNameChange = viewModel::onNameChange,
                onEmailChange = viewModel::onEmailChange,
                onPasswordChange = viewModel::onPasswordChange
            )
        }
        composable(ScreenRoutes.SetupProfile) {
            val viewModel: SetupProfileViewModel = viewModel()
            val fullNameState by viewModel.fullNameState.collectAsState()
            val schoolNameState by viewModel.schoolNameState.collectAsState()
            val classNameState by viewModel.classNameState.collectAsState()

            SetupProfileScreen(
                fullNameState = fullNameState,
                schoolNameState = schoolNameState,
                classNameState = classNameState,
                onFullNameChange = viewModel::onFullNameChange,
                onSchoolNameChange = viewModel::onSchoolNameChange,
                onClassNameChange = viewModel::onClassNameChange,
                onSaveClicked = { _, _, _ -> 
                    navController.navigate(ScreenRoutes.Home) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            )
        }
        composable(ScreenRoutes.Home) {
            HomeScreen(
                onEditProfile = { navController.navigate(ScreenRoutes.EditProfile) },
                onLogout = {
                    navController.navigate(ScreenRoutes.Login) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(ScreenRoutes.EditProfile) {
            EditProfileScreen(
                onBack = { navController.popBackStack() },
                onUpdate = { navController.popBackStack() }
            )
        }
    }
}
