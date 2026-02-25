package com.resultbookpro.app.presentation.analytics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import com.resultbookpro.app.presentation.common.theme.White

@Composable
fun AnalyticsScreen() {
    Scaffold {
        paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Text(
                text = "Performance Analytics",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            FilterChipGroup(listOf("Exam Wise", "Semester Wise", "Subject Wise"))

            Spacer(modifier = Modifier.height(24.dp))

            // Bar Chart Placeholder
            GraphPlaceholder("Exam Performance (Bar Chart)")

            Spacer(modifier = Modifier.height(24.dp))

            // Line Chart Placeholder
            GraphPlaceholder("Progress Over Time (Line Chart)")

            Spacer(modifier = Modifier.height(24.dp))

            // Summary Cards
            SummaryRow()
        }
    }
}

@Composable
fun FilterChipGroup(filters: List<String>) {
    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        items(filters.size) {
            // TODO: Implement FilterChip
            Text(text = filters[it], modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun GraphPlaceholder(title: String) {
    Card(
        modifier = Modifier.fillMaxWidth().height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = White)
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = title, color = Color.Gray)
        }
    }
}

@Composable
fun SummaryRow() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        SummaryCard(title = "Average %", value = "82%")
        SummaryCard(title = "Highest Score", value = "95/100")
        SummaryCard(title = "Lowest Score", value = "45/100")
    }
}

@Composable
fun SummaryCard(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = title, fontSize = 14.sp, color = Color.Gray)
        Text(text = value, fontSize = 20.sp, fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
fun AnalyticsScreenPreview() {
    ResultBookProTheme {
        AnalyticsScreen()
    }
}
