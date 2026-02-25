package com.resultbookpro.app.presentation.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
fun RegisterScreen(
    onRegisterClick: (String, String, String) -> Unit,
    nameState: String,
    emailState: String,
    passwordState: String,
    onNameChange: (String) -> Unit,
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
                text = "Create Account",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(48.dp))

            TextFieldInput(
                value = nameState,
                onValueChange = onNameChange,
                label = "Full Name",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

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
                text = "Register",
                onClick = { onRegisterClick(nameState, emailState, passwordState) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    ResultBookProTheme {
        var name by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        RegisterScreen(
            onRegisterClick = { _, _, _ -> },
            nameState = name,
            emailState = email,
            passwordState = password,
            onNameChange = { name = it },
            onEmailChange = { email = it },
            onPasswordChange = { password = it }
        )
    }
}
