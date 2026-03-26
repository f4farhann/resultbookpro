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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.resultbookpro.app.presentation.common.theme.PrimaryBlue
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
                contentPadding = PaddingValues(top = 0.dp, bottom = 100.dp)
            ) {
                state.sections.forEach { section ->
                    item(key = section.title) {
                        var isExpanded by rememberSaveable(section) {
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
                .padding(start = 14.dp)
                .size(24.dp)
                .clip(CircleShape)
                .background(PrimaryBlue),
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
            color = PrimaryBlue,
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
                .padding(start = 16.dp, top = 12.dp)
                .size(20.dp)
                .clip(CircleShape)
                .background(circleColor)
                .border(1.dp, Color.Black.copy(alpha = 0.2f), CircleShape),
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

        // Redesigned Card to match the image
        Card(
            modifier = Modifier
                .padding(end = 16.dp)
                .weight(1f),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
            border = BorderStroke(1.dp, Color.LightGray.copy(alpha = 0.5f))
        ) {
            Column {
                // Header with Year
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(PrimaryBlue.copy(alpha = 0.15f))
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = yearData.year,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        IconButton(onClick = onEditClick, modifier = Modifier.size(24.dp)) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit",
                                modifier = Modifier.size(16.dp),
                                tint = Color.Gray
                            )
                        }
                    }
                }

                Column(modifier = Modifier.padding(16.dp)) {
                    // Institution and Edit Icon
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = yearData.schoolName,
                            style = MaterialTheme.typography.titleSmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = Color.LightGray
                    )

                    Spacer(modifier = Modifier.height(6.dp))


                    // Details Row: Class | Board | Mark
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Class
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = yearData.className,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }

                        VerticalDivider(
                            modifier = Modifier
                                .height(16.dp)
                                .padding(horizontal = 8.dp),
                            color = Color.LightGray
                        )

                        // Board
                        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1.4f)) {
                            Icon(
                                imageVector = Icons.Default.Description,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Color.Gray
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = yearData.board,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium,
                                color = Color.Black
                            )
                        }

                        VerticalDivider(
                            modifier = Modifier
                                .height(16.dp)
                                .padding(horizontal = 8.dp),
                            color = Color.LightGray
                        )

                        // Marks
                        Text(
                            text = yearData.totalMarks,
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            modifier = Modifier.weight(0.6f),
                            textAlign = TextAlign.End
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Exam Pills (1st Term, 2nd Term, etc.)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        yearData.exams.forEachIndexed { index, exam ->
                            val pillColor = when (index) {
                                0 -> Color(0xFFE3F2FD) // Light Blue for 1st Term
                                1 -> Color(0xFFE8F5E9) // Light Green for 2nd Term
                                2 -> Color(0xFFFFEBEE) // Light Red for 3rd Term
                                else -> Color.LightGray.copy(alpha = 0.3f)
                            }

                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = pillColor,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    text = exam.name,
                                    modifier = Modifier.padding(vertical = 8.dp),
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Black.copy(alpha = 0.7f),
                                    textAlign = TextAlign.Center
                                )
                            }
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
