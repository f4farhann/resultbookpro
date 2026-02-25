package com.resultbookpro.app.presentation.upcoming

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme

@Composable
fun UpcomingScreen(onAddReminderClicked: () -> Unit) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddReminderClicked) {
                Icon(Icons.Filled.Add, contentDescription = "Add Reminder")
            }
        }
    ) {
        paddingValues ->
        // Mock data for preview
        val upcomingExams = listOf<String>() // emptyList<String>()

        if (upcomingExams.isEmpty()) {
            EmptyState("No upcoming exams or tasks. Add a reminder!")
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                item {
                    Text(
                        text = "Upcoming Exams & Tasks",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                // TODO: Add items for upcoming exams
            }
        }
    }
}

@Composable
fun EmptyState(message: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: Add an illustration
        Text(text = message, color = Color.Gray, fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun UpcomingScreenPreview() {
    ResultBookProTheme {
        UpcomingScreen(onAddReminderClicked = { })
    }
}
