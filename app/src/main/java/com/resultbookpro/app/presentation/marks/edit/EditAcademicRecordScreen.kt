package com.resultbookpro.app.presentation.marks.edit

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.resultbookpro.app.presentation.common.components.TextFieldInput
import com.resultbookpro.app.presentation.common.theme.PrimaryBlue

data class Subject(
    val name: String = "",
    val totalMarks: String = "100",
    val obtainedMarks: String = ""
)

data class ExamEditState(
    val name: String,
    val subjects: List<Subject> = listOf(Subject())
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAcademicRecordScreen(
    year: String,
    initialSchoolName: String,
    initialClassName: String,
    initialBoard: String,
    initialExams: List<String>,
    onBack: () -> Unit,
    onSave: () -> Unit
) {
    var schoolName by remember { mutableStateOf(initialSchoolName) }
    var className by remember { mutableStateOf(initialClassName) }
    var board by remember { mutableStateOf(initialBoard) }
    var academicYear by remember { mutableStateOf(year) }

    var exams by remember { 
        mutableStateOf(initialExams.map { ExamEditState(it) }) 
    }
    var selectedExamIndex by remember { mutableStateOf(0) }

    val hasChanges = remember(schoolName, className, board, academicYear, exams) {
        schoolName != initialSchoolName || 
        className != initialClassName || 
        board != initialBoard || 
        academicYear != year ||
        exams.any { it.subjects.any { sub -> sub.name.isNotEmpty() || sub.obtainedMarks.isNotEmpty() } }
    }

    var showExitDialog by remember { mutableStateOf(false) }

    BackHandler {
        if (hasChanges) showExitDialog = true else onBack()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit academic record - $year", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { if (hasChanges) showExitDialog = true else onBack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    if (hasChanges) {
                        IconButton(onClick = onSave) {
                            Icon(Icons.Default.Save, contentDescription = "Save", tint = Color.White)
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = PrimaryBlue,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Basic Info Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    TextFieldInput(value = academicYear, onValueChange = { academicYear = it }, label = "Academic Year", modifier = Modifier.fillMaxWidth())
                    TextFieldInput(value = schoolName, onValueChange = { schoolName = it }, label = "Institute Name", modifier = Modifier.fillMaxWidth())
                    TextFieldInput(value = className, onValueChange = { className = it }, label = "Class/Sem", modifier = Modifier.fillMaxWidth())
                    TextFieldInput(value = board, onValueChange = { board = it }, label = "Board", modifier = Modifier.fillMaxWidth())
                }
            }

            // Exam Selection (Horizontal Scroll)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                exams.forEachIndexed { index, exam ->
                    val isSelected = selectedExamIndex == index
                    Button(
                        onClick = { selectedExamIndex = index },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) PrimaryBlue else Color.LightGray.copy(alpha = 0.3f),
                            contentColor = if (isSelected) Color.White else Color.Black
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(exam.name, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Subjects for Selected Exam
            val currentExam = exams[selectedExamIndex]
            Text("Subjects & Marks", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)

            currentExam.subjects.forEachIndexed { subIndex, subject ->
                SubjectEntryRow(
                    subject = subject,
                    onUpdate = { updatedSub ->
                        val newExams = exams.toMutableList()
                        val newSubjects = currentExam.subjects.toMutableList()
                        newSubjects[subIndex] = updatedSub
                        newExams[selectedExamIndex] = currentExam.copy(subjects = newSubjects)
                        exams = newExams
                    }
                )
            }

            // Add Subject Button
            TextButton(
                onClick = {
                    val newExams = exams.toMutableList()
                    val newSubjects = currentExam.subjects.toMutableList() + Subject()
                    newExams[selectedExamIndex] = currentExam.copy(subjects = newSubjects)
                    exams = newExams
                },
                modifier = Modifier.align(Alignment.End)
            ) {
                Icon(Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text("Add Subject")
            }

            // Calculation Summary
            CalculationSummaryCard(currentExam.subjects)
        }

        if (showExitDialog) {
            AlertDialog(
                onDismissRequest = { showExitDialog = false },
                title = { Text("Unsaved Changes") },
                text = { Text("Do you want to save the changes or discard them?") },
                confirmButton = {
                    Button(onClick = { onSave(); showExitDialog = false }) { Text("Save") }
                },
                dismissButton = {
                    TextButton(onClick = { showExitDialog = false; onBack() }) { Text("Discard") }
                }
            )
        }
    }
}

@Composable
fun SubjectEntryRow(
    subject: Subject,
    onUpdate: (Subject) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = subject.name,
            onValueChange = { onUpdate(subject.copy(name = it)) },
            label = { Text("Subject", fontSize = 10.sp) },
            modifier = Modifier.weight(2f),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = subject.obtainedMarks,
            onValueChange = { if (it.all { char -> char.isDigit() }) onUpdate(subject.copy(obtainedMarks = it)) },
            label = { Text("Marks", fontSize = 10.sp) },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp)
        )
        OutlinedTextField(
            value = subject.totalMarks,
            onValueChange = { if (it.all { char -> char.isDigit() }) onUpdate(subject.copy(totalMarks = it)) },
            label = { Text("Max", fontSize = 10.sp) },
            modifier = Modifier.weight(1f),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
fun CalculationSummaryCard(subjects: List<Subject>) {
    val totalObtained = subjects.sumOf { it.obtainedMarks.toDoubleOrNull() ?: 0.0 }
    val totalMax = subjects.sumOf { it.totalMarks.toDoubleOrNull() ?: 0.0 }
    val percentage = if (totalMax > 0) (totalObtained / totalMax) * 100 else 0.0
    val cgpa = percentage / 9.5 // Basic calculation

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = PrimaryBlue.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SummaryRow("Total Marks", "${totalObtained.toInt()} / ${totalMax.toInt()}")
            SummaryRow("Percentage", "%.2f %%".format(percentage))
            SummaryRow("CGPA", "%.2f".format(cgpa))
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontWeight = FontWeight.Medium)
        Text(value, fontWeight = FontWeight.Bold, color = PrimaryBlue)
    }
}
