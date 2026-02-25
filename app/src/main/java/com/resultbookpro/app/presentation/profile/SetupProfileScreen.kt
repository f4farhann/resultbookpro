package com.resultbookpro.app.presentation.profile

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
fun SetupProfileScreen(
    onSaveClicked: (String, String, String) -> Unit,
    fullNameState: String,
    schoolNameState: String,
    classNameState: String,
    onFullNameChange: (String) -> Unit,
    onSchoolNameChange: (String) -> Unit,
    onClassNameChange: (String) -> Unit
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
                text = "Setup Your Profile",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(48.dp))

            TextFieldInput(
                value = fullNameState,
                onValueChange = onFullNameChange,
                label = "Full Name",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextFieldInput(
                value = schoolNameState,
                onValueChange = onSchoolNameChange,
                label = "School/College Name",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextFieldInput(
                value = classNameState,
                onValueChange = onClassNameChange,
                label = "Class / Semester",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            PrimaryButton(
                text = "Save",
                onClick = { onSaveClicked(fullNameState, schoolNameState, classNameState) },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SetupProfileScreenPreview() {
    ResultBookProTheme {
        var fullName by remember { mutableStateOf("") }
        var schoolName by remember { mutableStateOf("") }
        var className by remember { mutableStateOf("") }

        SetupProfileScreen(
            onSaveClicked = { _, _, _ -> },
            fullNameState = fullName,
            schoolNameState = schoolName,
            classNameState = className,
            onFullNameChange = { fullName = it },
            onSchoolNameChange = { schoolName = it },
            onClassNameChange = { className = it }
        )
    }
}
