package com.resultbookpro.app.presentation.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.resultbookpro.app.presentation.common.theme.PrimaryBlue
import com.resultbookpro.app.presentation.common.theme.ResultBookProTheme
import com.resultbookpro.app.presentation.common.theme.White

@Composable
fun ProfileScreen(
    onEditProfile: () -> Unit,
    onLogout: () -> Unit,
    viewModel: ProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    var showStudyLevelDialog by remember { mutableStateOf(false) }
    var showCourseTypeDialog by remember { mutableStateOf(false) }
    var showClassSemesterDialog by remember { mutableStateOf(false) }
    var showSchoolCollegeDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    val isCollegeLevel = state.studyLevel == "Postgraduate (PG)" || state.studyLevel == "Undergraduate (UG)" || state.studyLevel == "PHD"
    val isSecondaryAbove = isCollegeLevel || state.studyLevel == "Senior Secondary School (11–12)"
    val isSchoolLevel = !isCollegeLevel

    if (showStudyLevelDialog) {
        val options = listOf(
            "PHD",
            "Postgraduate (PG)",
            "Undergraduate (UG)",
            "Senior Secondary School (11–12)",
            "Secondary School (9–10)",
            "Middle School (6–8)",
            "Primary School (1–5)",
            "Pre-Primary School (Nursery / KG)"
        )
        SelectionDialog(
            title = "Select Study Level",
            options = options,
            onDismiss = { showStudyLevelDialog = false },
            onSelect = {
                viewModel.updateStudyLevel(it)
                showStudyLevelDialog = false
            }
        )
    }

    if (showCourseTypeDialog) {
        val options = when (state.studyLevel) {
            "Senior Secondary School (11–12)" -> listOf("Science", "Arts", "Commerce", "Vocational", "Other")
            else -> listOf("Regular", "Private", "Distance", "Other")
        }
        SelectionDialog(
            title = "Select Course Type",
            options = options,
            onDismiss = { showCourseTypeDialog = false },
            onSelect = {
                viewModel.updateCourseType(it)
                showCourseTypeDialog = false
            }
        )
    }

    if (showClassSemesterDialog) {
        val options = when (state.studyLevel) {
            "Pre-Primary School (Nursery / KG)" -> listOf("Nursery", "LKG", "UKG")
            "Primary School (1–5)" -> (1..5).map { "Class $it" }
            "Middle School (6–8)" -> (6..8).map { "Class $it" }
            "Secondary School (9–10)" -> listOf("Class 9", "Class 10")
            "Senior Secondary School (11–12)" -> listOf("Class 11", "Class 12")
            "Undergraduate (UG)" -> (1..8).map { "Semester $it" }
            "Postgraduate (PG)" -> (1..4).map { "Semester $it" }
            "PHD" -> (1..12).map { "Semester $it" }
            else -> emptyList()
        }
        SelectionDialog(
            title = if (isSchoolLevel) "Select Class" else "Select Semester",
            options = options,
            onDismiss = { showClassSemesterDialog = false },
            onSelect = {
                viewModel.updateClassOrSemester(it)
                showClassSemesterDialog = false
            }
        )
    }

    if (showSchoolCollegeDialog) {
        InputDialog(
            title = if (isSchoolLevel) "School Name" else "College / University Name",
            initialValue = state.schoolCollegeName,
            onDismiss = { showSchoolCollegeDialog = false },
            onConfirm = {
                viewModel.updateSchoolCollegeName(it)
                showSchoolCollegeDialog = false
            }
        )
    }

    if (showAboutDialog) {
        InputDialog(
            title = "About",
            initialValue = state.about,
            onDismiss = { showAboutDialog = false },
            onConfirm = {
                viewModel.updateAbout(it)
                showAboutDialog = false
            },
            singleLine = false
        )
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA)),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            ProfileHeaderSection(
                name = state.fullName,
                email = state.email,
                onEditClick = onEditProfile
            )
        }

        item {
            ProfileInfoSection(
                label = "Study Level",
                value = state.studyLevel,
                icon = Icons.Default.Layers,
                isMissing = viewModel.isFieldMissing(state.studyLevel),
                onClick = { showStudyLevelDialog = true }
            )
        }

        item {
            val label = if (isSchoolLevel) "School Name" else "College / University Name"
            ProfileInfoSection(
                label = label,
                value = state.schoolCollegeName,
                icon = Icons.Default.School,
                isMissing = viewModel.isFieldMissing(state.schoolCollegeName),
                onClick = { showSchoolCollegeDialog = true }
            )
        }

        if (isSecondaryAbove) {
            item {
                ProfileInfoSection(
                    label = "Course Type",
                    value = state.courseType ?: "",
                    icon = Icons.Default.Book,
                    isMissing = viewModel.isFieldMissing(state.courseType),
                    onClick = { showCourseTypeDialog = true }
                )
            }
        }

        item {
            val label = if (isSchoolLevel) "Class" else "Semester"
            ProfileInfoSection(
                label = label,
                value = state.classOrSemester,
                icon = Icons.Default.TableRows,
                isMissing = viewModel.isFieldMissing(state.classOrSemester),
                onClick = { showClassSemesterDialog = true }
            )
        }

        item {
            ProfileInfoSection(
                label = "About",
                value = state.about,
                icon = Icons.Default.Info,
                isMissing = viewModel.isFieldMissing(state.about),
                isMultiLine = true,
                onClick = { showAboutDialog = true }
            )
        }

        item {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Text("Logout", color = Red, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun SelectionDialog(
    title: String,
    options: List<String>,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = White,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            LazyColumn {
                items(options.size) { index ->
                    val option = options[index]
                    Text(
                        text = option,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(option) }
                            .padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                    if (index < options.size - 1) {
                        HorizontalDivider(color = Color.LightGray.copy(alpha = 0.5f))
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) { 
                Text("Cancel", color = PrimaryBlue) 
            }
        }
    )
}

@Composable
fun InputDialog(
    title: String,
    initialValue: String,
    onDismiss: () -> Unit,
    onConfirm: (String) -> Unit,
    singleLine: Boolean = true
) {
    var text by remember { mutableStateOf(initialValue) }
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = White,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = singleLine,
                maxLines = if (singleLine) 1 else 5,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = PrimaryBlue,
                    unfocusedBorderColor = Color.LightGray
                )
            )
        },
        confirmButton = {
            TextButton(onClick = { onConfirm(text) }) { 
                Text("Save", color = PrimaryBlue) 
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { 
                Text("Cancel", color = Color.Gray) 
            }
        }
    )
}

@Composable
fun ProfileHeaderSection(
    name: String,
    email: String,
    onEditClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(70.dp),
                shape = CircleShape,
                color = PrimaryBlue.copy(alpha = 0.1f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = if (name.length >= 2) name.take(2).uppercase() else name.uppercase(),
                        style = MaterialTheme.typography.headlineSmall,
                        color = PrimaryBlue,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            IconButton(onClick = onEditClick) {
                Icon(Icons.Default.Edit, contentDescription = "Edit Profile", tint = PrimaryBlue)
            }
        }
    }
}

@Composable
fun ProfileInfoSection(
    label: String,
    value: String,
    icon: ImageVector,
    isMissing: Boolean,
    isMultiLine: Boolean = false,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = if (isMultiLine) Alignment.Top else Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (isMissing) Color(0xFFFBC02D) else PrimaryBlue,
                modifier = Modifier.size(24.dp)
            )

            Column(
                modifier = Modifier
                    .padding(start = 16.dp)
                    .weight(1f)
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.labelMedium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(2.dp))
                if (isMissing) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Required details missing",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFFFBC02D),
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = Color(0xFFFBC02D),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                } else {
                    Text(
                        text = value,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black // Ensuring text color is black for visibility
                    )
                }
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = Color.LightGray,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ResultBookProTheme {
        ProfileScreen(onEditProfile = {}, onLogout = {})
    }
}
