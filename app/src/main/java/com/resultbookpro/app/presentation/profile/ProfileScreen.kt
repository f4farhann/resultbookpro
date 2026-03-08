package com.resultbookpro.app.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    val isSchoolLevel = state.studyLevel == "Nursery/KG" || state.studyLevel == "School (1–10)" || state.studyLevel == "Higher Secondary (11–12)"
    val isCollegeLevel = state.studyLevel == "College / University"
    val isAboveSchool10 = state.studyLevel == "Higher Secondary (11–12)" || state.studyLevel == "College / University"

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F7FA)),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        // Section 1: Header Info
        item {
            ProfileHeaderSection(
                name = state.fullName,
                email = state.email,
                onEditClick = onEditProfile
            )
        }

        // Section 2: School/College Name
        item {
            val label = if (isSchoolLevel) "School Name" else "College / University Name"
            ProfileInfoSection(
                label = label,
                value = state.schoolCollegeName,
                icon = Icons.Default.School,
                isMissing = viewModel.isFieldMissing(state.schoolCollegeName)
            )
        }

        // Section 3: Study Level
        item {
            ProfileInfoSection(
                label = "Study Level",
                value = state.studyLevel,
                icon = Icons.Default.Layers,
                isMissing = viewModel.isFieldMissing(state.studyLevel)
            )
        }

        // Section 4: Course Type (if above school 10+)
        if (isAboveSchool10) {
            item {
                ProfileInfoSection(
                    label = "Course Type",
                    value = state.courseType ?: "",
                    icon = Icons.Default.Book,
                    isMissing = viewModel.isFieldMissing(state.courseType)
                )
            }
        }

        // Section 5: Class or Semester
        item {
            val label = if (isSchoolLevel) "Class" else "Semester"
            ProfileInfoSection(
                label = label,
                value = state.classOrSemester,
                icon = Icons.Default.Class,
                isMissing = viewModel.isFieldMissing(state.classOrSemester)
            )
        }

        // Section 6: About Section
        item {
            ProfileInfoSection(
                label = "About",
                value = state.about,
                icon = Icons.Default.Info,
                isMissing = viewModel.isFieldMissing(state.about),
                isMultiLine = true
            )
        }

        // Logout Button
        item {
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red.copy(alpha = 0.8f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Logout", color = White, fontWeight = FontWeight.Bold)
            }
        }
    }
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
                        text = name.take(2).uppercase(),
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
                    fontWeight = FontWeight.Bold
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
    isMultiLine: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
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
                        fontWeight = FontWeight.Medium
                    )
                }
            }
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
