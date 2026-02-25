package com.resultbookpro.app.presentation.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultbookpro.app.presentation.common.components.PrimaryButton
import com.resultbookpro.app.presentation.common.theme.PrimaryBlue
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme
import com.resultbookpro.app.presentation.common.theme.White

@Composable
fun CategoryScreen(
    onCategorySelected: (String) -> Unit,
    onContinueClicked: () -> Unit,
    selectedCategory: String?
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
                text = "Select Your Study Level",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(32.dp))

            val categories = listOf("School (1–10)", "Higher Secondary (11–12)", "College / University")

            categories.forEach { category ->
                CategoryCard(
                    text = category,
                    isSelected = selectedCategory == category,
                    onClick = { onCategorySelected(category) }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            Spacer(modifier = Modifier.weight(1f))

            PrimaryButton(
                text = "Continue",
                onClick = onContinueClicked,
                enabled = selectedCategory != null
            )
        }
    }
}

@Composable
fun CategoryCard(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) PrimaryBlue else White,
            contentColor = if (isSelected) White else Color.Black
        ),
        border = if (!isSelected) BorderStroke(1.dp, Color.LightGray) else null,
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = text,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenPreview() {
    ResultBookProTheme {
        var selectedCategory by remember { mutableStateOf<String?>(null) }
        CategoryScreen(
            onCategorySelected = { selectedCategory = it },
            onContinueClicked = { },
            selectedCategory = selectedCategory
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryScreenSelectedPreview() {
    ResultBookProTheme {
        CategoryScreen(
            onCategorySelected = { },
            onContinueClicked = { },
            selectedCategory = "School (1–10)"
        )
    }
}
