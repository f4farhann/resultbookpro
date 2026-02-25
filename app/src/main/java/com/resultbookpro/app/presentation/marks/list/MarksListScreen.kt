package com.resultbookpro.app.presentation.marks.list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.resultbookpro.app.presentation.common.components.ScoreCard
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarksListScreen(
    onAddMark: () -> Unit, // Will be used to navigate to AddMarkScreen
    onMarkSwiped: (Int) -> Unit // Assuming ID is Int
) {
    Scaffold {
        paddingValues ->
        // Mock data for preview
        val marks = listOf(
            Triple("Physics", "Finals", "78/100") to "12 Dec 2023",
            Triple("Chemistry", "Mid-Term", "92/100") to "15 Nov 2023",
            Triple("Biology", "Unit Test", "65/100") to "30 Oct 2023"
        )

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(marks.size) { index ->
                val state = rememberSwipeToDismissBoxState(
                    confirmValueChange = { 
                        if (it == SwipeToDismissBoxValue.EndToStart) {
                            onMarkSwiped(index) 
                        }
                        true 
                    }
                )

                SwipeToDismissBox(
                    state = state,
                    backgroundContent = {
                        val color = when (state.dismissDirection) {
                            SwipeToDismissBoxValue.EndToStart -> Color.Red.copy(alpha = 0.8f)
                            else -> Color.Transparent
                        }
                        Box(
                            Modifier.fillMaxSize().background(color).padding(horizontal = 20.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(Icons.Default.Delete, contentDescription = "Delete", tint = Color.White)
                        }
                    },
                    content = {
                        ScoreCard(
                            subject = marks[index].first.first,
                            exam = marks[index].first.second,
                            score = marks[index].first.third,
                            date = marks[index].second
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarksListScreenPreview() {
    ResultBookProTheme {
        MarksListScreen(onAddMark = {}, onMarkSwiped = {})
    }
}
