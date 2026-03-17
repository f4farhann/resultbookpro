package com.resultbookpro.app.presentation.marks.list

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

enum class StudyStatus {
    COMPLETED, ONGOING, PENDING
}

enum class AcademicType {
    SCHOOL, HIGHER_ED
}

data class ExamData(
    val name: String,
    val marks: String = ""
)

data class AcademicYearData(
    val year: String,
    val schoolName: String = "Enter School Name",
    val className: String,
    val board: String = "CBSE",
    val totalMarks: String = "90%",
    val status: StudyStatus = StudyStatus.PENDING,
    val type: AcademicType,
    val exams: List<ExamData> = emptyList()
)

data class StudySection(
    val title: String,
    val levels: List<AcademicYearData>,
    val type: AcademicType
)

data class MarksListState(
    val selectedStudyLevel: String = "",
    val sections: List<StudySection> = emptyList()
)

class MarksListViewModel : ViewModel() {
    private val _state = MutableStateFlow(MarksListState())
    val state = _state.asStateFlow()

    private val allSections = listOf(
        StudySection(
            "PhD",
            listOf(
                AcademicYearData("2024", className = "PhD 4th yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem"))),
                AcademicYearData("2023", className = "PhD 3rd yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem"))),
                AcademicYearData("2022", className = "PhD 2nd yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem"))),
                AcademicYearData("2021", className = "PhD 1st yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem")))
            ),
            AcademicType.HIGHER_ED
        ),
        StudySection(
            "Postgraduate (PG)",
            listOf(
                AcademicYearData("2020", className = "PG 2nd yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem"))),
                AcademicYearData("2019", className = "PG 1st yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem")))
            ),
            AcademicType.HIGHER_ED
        ),
        StudySection(
            "Undergraduate (UG)",
            listOf(
                AcademicYearData("2018", className = "UG 4th yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem"))),
                AcademicYearData("2017", className = "UG 3rd yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem"))),
                AcademicYearData("2016", className = "UG 2nd yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem"))),
                AcademicYearData("2015", className = "UG 1st yr", type = AcademicType.HIGHER_ED, exams = listOf(ExamData("1 Sem"), ExamData("2 Sem")))
            ),
            AcademicType.HIGHER_ED
        ),
        StudySection(
            "Senior Secondary School (11 to 12)",
            listOf(
                AcademicYearData("2014", className = "Class 12", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2013", className = "Class 11", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term")))
            ),
            AcademicType.SCHOOL
        ),
        StudySection(
            "Secondary School (9 to 10)",
            listOf(
                AcademicYearData("2012", className = "Class 10", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2011", className = "Class 9", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term")))
            ),
            AcademicType.SCHOOL
        ),
        StudySection(
            "Middle School (6 to 8)",
            listOf(
                AcademicYearData("2010", className = "Class 8", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2009", className = "Class 7", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2008", className = "Class 6", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term")))
            ),
            AcademicType.SCHOOL
        ),
        StudySection(
            "Primary School (1 to 5)",
            listOf(
                AcademicYearData("2007", className = "Class 5", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2006", className = "Class 4", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2005", className = "Class 3", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2004", className = "Class 2", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2003", className = "Class 1", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term")))
            ),
            AcademicType.SCHOOL
        ),
        StudySection(
            "Pre-Primary School (Nursery / kg)",
            listOf(
                AcademicYearData("2002", className = "UKG", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2001", className = "LKG", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term"))),
                AcademicYearData("2000", className = "Nursery", type = AcademicType.SCHOOL, exams = listOf(ExamData("1st Term"), ExamData("2nd Term"), ExamData("3rd Term")))
            ),
            AcademicType.SCHOOL
        )
    )

    fun updateSections(selectedLevel: String) {
        val filtered = mutableListOf<StudySection>()
        var found = false

        // selectedLevel might be "Undergraduate (UG)" or "UG" or "Class 10"
        // We need a robust way to match. The requirement says:
        // "if selected ug then from pre- primary school to ug study section it will show"
        // Our allSections is PhD -> Pre-primary.

        for (section in allSections) {
            if (section.title.contains(selectedLevel, ignoreCase = true) ||
                selectedLevel.contains(section.title, ignoreCase = true) ||
                section.levels.any { it.className.contains(selectedLevel, ignoreCase = true) }) {
                found = true
            }
            if (found) {
                // To make it look like the photo, mark some as completed and one as ongoing
                val updatedLevels = section.levels.mapIndexed { index, yearData ->
                    if (filtered.isEmpty() && index == 0) {
                        yearData.copy(status = StudyStatus.ONGOING, schoolName = "Example University", totalMarks = "Goin on")
                    } else {
                        yearData.copy(status = StudyStatus.COMPLETED, schoolName = "ABC School", totalMarks = "85%")
                    }
                }
                filtered.add(section.copy(levels = updatedLevels))
            }
        }

        // If not found (e.g. empty or unknown), maybe show all or just the bottom one.
        // But per requirement, we should show from that level downwards.
        // If "found" never became true, it means the selectedLevel is above our PhD?
        // Or it's something not in our list.

        _state.value = _state.value.copy(
            selectedStudyLevel = selectedLevel,
            sections = filtered.ifEmpty { allSections } // Fallback
        )
    }
}
