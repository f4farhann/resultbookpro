package com.resultbookpro.app.presentation.marks.edit

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class EditAcademicRecordState(
    val year: String = "year",
    val initialSchoolName: String = "Previous Institution",
    val initialClassName: String = "UKG",
    val initialBoard: String = "CBSE",
    val initialExams: List<String> = listOf("1st Term", "2nd Term", "3rd Term"),
)

class EditAcademicRecordViewModel : ViewModel() {

    private val _state = MutableStateFlow(EditAcademicRecordState())
    val state = _state.asStateFlow()


   //                        year = year,
//                        initialSchoolName = "Previous Institution",
//                        initialClassName = "UKG",
//                        initialBoard = "CBSE",
//                        initialExams = listOf("1st Term", "2nd Term", "3rd Term"),
}