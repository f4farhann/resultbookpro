package com.resultbookpro.app.presentation.marks.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme

@Composable
fun MarksListScreen(
    studyLevelFromProfile: String = "Undergraduate (UG)",
    viewModel: MarksListViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(studyLevelFromProfile) {
        viewModel.updateSections(studyLevelFromProfile)
    }

    Scaffold(
        topBar = {}
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Straight square vertical line on the left
            Box(
                modifier = Modifier
                    .padding(start = 24.dp)
                    .fillMaxHeight()
                    .width(4.dp)
                    .background(Color.Gray.copy(alpha = 0.3f))
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(top = 0.dp, bottom = 0.dp)
            ) {
                state.sections.forEach { section ->
                    item(key = section.title) {
                        var isExpanded by rememberSaveable(section) {  // Use section.id as key
                            mutableStateOf(true)
                        }

                        SectionHeaderItem(
                            section = section,
                            isExpanded = isExpanded,
                            onExpandClick = { isExpanded = !isExpanded }
                        )
                        
                        AnimatedVisibility(
                            visible = isExpanded,
                            enter = expandVertically(expandFrom = Alignment.Top) + fadeIn(),
                            exit = shrinkVertically(shrinkTowards = Alignment.Top) + fadeOut()
                            ) {
                            Column {
                                section.levels.forEach { yearData ->
                                    AcademicYearItem(
                                        yearData = yearData,
                                        onEditClick = { }
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun SectionHeaderItem(
    section: StudySection,
    isExpanded: Boolean,
    onExpandClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 1st circle: Blue for section always
        Box(
            modifier = Modifier
                .padding(start = 14.dp) // Centers on the 24dp line (24 + 4/2 - 20/2) approx
                .size(24.dp)
                .clip(CircleShape)
                .background(Color(0xFF2196F3)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color.White)
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Section box
        Surface(
            modifier = Modifier
                .padding(end = 16.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(6.dp),
            color = Color(0xFF2196F3),
        ) {
            Row(
                modifier = Modifier.height(IntrinsicSize.Min),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = section.title,
                    modifier = Modifier
                        .weight(3f)
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color.White
                )
                VerticalDivider(color = Color.White)
                IconButton(onClick = onExpandClick, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand",
                        modifier = Modifier.size(18.dp),
                        tint = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun AcademicYearItem(
    yearData: AcademicYearData,
    onEditClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.Top
    ) {
        // Status circle
        val (circleColor, icon) = when (yearData.status) {
            StudyStatus.COMPLETED -> Color(0xFF4CAF50) to Icons.Default.Check
            StudyStatus.ONGOING -> Color(0xFFFFEB3B) to Icons.Default.PlayArrow
            StudyStatus.PENDING -> Color.LightGray to null
        }

        Box(
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(circleColor)
                .border(1.dp, Color.Black, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (icon != null) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = if (yearData.status == StudyStatus.ONGOING) Color.Black else Color.White
                )
            }
        }

        Spacer(modifier = Modifier.width(20.dp))

        Column(
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f)
        ) {
            // 1st box: Year
            Surface(
                shape = RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.width(100.dp)
            ) {
                Text(
                    text = yearData.year,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }

            // 2nd box: School Name | Edit icon
            Surface(
                color = Color.White,
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = yearData.schoolName,
                        modifier = Modifier
                            .weight(3f)
                            .padding(8.dp),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.Black
                    )
                    VerticalDivider(color = Color.Black)
                    IconButton(onClick = onEditClick, modifier = Modifier.size(36.dp)) {
                        Icon(
                            Icons.Default.Edit,
                            contentDescription = "Edit",
                            modifier = Modifier.size(18.dp),
                            tint = Color.Black
                        )
                    }
                }
            }

            // 3rd box: Class | Board | Mark
            Surface(
                color = Color.White,
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = yearData.className,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                    VerticalDivider(color = Color.Black)
                    Text(
                        text = yearData.board,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                    VerticalDivider(color = Color.Black)
                    Text(
                        text = yearData.totalMarks,
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp),
                        fontSize = 13.sp,
                        color = Color.Black
                    )
                }
            }

            // 4th box: Term wise or Semester wise marks
            Surface(
                shape = RoundedCornerShape(bottomStart = 4.dp, bottomEnd = 4.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Color.Black),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    yearData.exams.forEachIndexed { index, exam ->
                        Text(
                            text = exam.name,
                            modifier = Modifier
                                .weight(1f)
                                .padding(8.dp),
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                        if (index < yearData.exams.size - 1) {
                            VerticalDivider(color = Color.Black)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MarksListScreenPreview() {
    ResultBookProTheme {
        MarksListScreen()
    }
}
