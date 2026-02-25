package com.resultbookpro.app.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.resultbookpro.app.presentation.auth.login.LoginScreen
import com.resultbookpro.app.presentation.auth.register.RegisterScreen
import com.resultbookpro.app.presentation.category.CategoryScreen
import com.resultbookpro.app.presentation.home.HomeScreen
import com.resultbookpro.app.presentation.profile.SetupProfileScreen
import com.resultbookpro.app.presentation.splash.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = ScreenRoutes.Splash) {
        composable(ScreenRoutes.Splash) {
            SplashScreen(onTimeout = { 
                navController.navigate(ScreenRoutes.Category) {
                    popUpTo(ScreenRoutes.Splash) { inclusive = true }
                }
            })
        }
        composable(ScreenRoutes.Category) {
            var selectedCategory by remember { mutableStateOf<String?>(null) }
            CategoryScreen(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it },
                onContinueClicked = { navController.navigate(ScreenRoutes.Login) }
            )
        }
        composable(ScreenRoutes.Login) {
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            LoginScreen(
                emailState = email,
                passwordState = password,
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onLoginClick = { _, _ -> 
                    navController.navigate(ScreenRoutes.Home) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                 },
                onRegisterClick = { navController.navigate(ScreenRoutes.Register) }
            )
        }
        composable(ScreenRoutes.Register) {
            var name by remember { mutableStateOf("") }
            var email by remember { mutableStateOf("") }
            var password by remember { mutableStateOf("") }
            RegisterScreen(
                nameState = name,
                emailState = email,
                passwordState = password,
                onNameChange = { name = it },
                onEmailChange = { email = it },
                onPasswordChange = { password = it },
                onRegisterClick = { _, _, _ -> navController.navigate(ScreenRoutes.SetupProfile) }
            )
        }
        composable(ScreenRoutes.SetupProfile) {
            var name by remember { mutableStateOf("") }
            var school by remember { mutableStateOf("") }
            var className by remember { mutableStateOf("") }
            SetupProfileScreen(
                fullNameState = name,
                schoolNameState = school,
                classNameState = className,
                onFullNameChange = { name = it },
                onSchoolNameChange = { school = it },
                onClassNameChange = { className = it },
                onSaveClicked = { _, _, _ -> 
                    navController.navigate(ScreenRoutes.Home) {
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }
                    }
                }
            )
        }
        composable(ScreenRoutes.Home) {
            HomeScreen()
        }
    }
}
