package com.resultbookpro.app.presentation.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultbookpro.app.presentation.common.components.PrimaryButton
import com.resultbookpro.app.presentation.common.components.TextFieldInput
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme

@Composable
fun LoginScreen(
    onLoginClick: (String, String) -> Unit,
    onRegisterClick: () -> Unit,
    emailState: String,
    passwordState: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Scaffold {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome Back",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(48.dp))

            TextFieldInput(
                value = emailState,
                onValueChange = onEmailChange,
                label = "Email",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextFieldInput(
                value = passwordState,
                onValueChange = onPasswordChange,
                label = "Password",
                isPasswordField = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryButton(
                text = "Login",
                onClick = { onLoginClick(emailState, passwordState) },
                modifier = Modifier.fillMaxWidth()
            )

            TextButton(onClick = onRegisterClick) {
                Text(text = "Don’t have an account? Register")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    ResultBookProTheme {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        LoginScreen(
            onLoginClick = { _, _ -> },
            onRegisterClick = { },
            emailState = email,
            passwordState = password,
            onEmailChange = { email = it },
            onPasswordChange = { password = it }
        )
    }
}
