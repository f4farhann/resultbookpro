package com.resultbookpro.app.presentation.marks.add

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resultbookpro.app.presentation.common.components.PrimaryButton
import com.resultbookpro.app.presentation.common.components.TextFieldInput
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme

@Composable
fun AddMarkScreen(
    onSaveClicked: () -> Unit,
) {
    var semester by remember { mutableStateOf("") }
    var subject by remember { mutableStateOf("") }
    var exam by remember { mutableStateOf("") }
    var obtainedMarks by remember { mutableStateOf("") }
    var totalMarks by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }

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
            // TODO: Replace with Dropdown Menus
            TextFieldInput(value = semester, onValueChange = { semester = it }, label = "Semester", modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            TextFieldInput(value = subject, onValueChange = { subject = it }, label = "Subject", modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            TextFieldInput(value = exam, onValueChange = { exam = it }, label = "Exam Name", modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            TextFieldInput(value = obtainedMarks, onValueChange = { obtainedMarks = it }, label = "Obtained Marks", modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            TextFieldInput(value = totalMarks, onValueChange = { totalMarks = it }, label = "Total Marks", modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(16.dp))
            // TODO: Replace with Date Picker
            TextFieldInput(value = date, onValueChange = { date = it }, label = "Date", modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(32.dp))

            PrimaryButton(text = "Save Mark", onClick = onSaveClicked)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddMarkScreenPreview() {
    ResultBookProTheme {
        AddMarkScreen(onSaveClicked = {})
    }
}
