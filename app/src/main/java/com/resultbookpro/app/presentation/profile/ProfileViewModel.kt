package com.resultbookpro.app.presentation.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileState(
    val fullName: String = "Farhan Haider",
    val email: String = "farhanhaider@gmail.com",
    val profilePicUrl: String? = null,
    val schoolCollegeName: String = "",
    val studyLevel: String = "", 
    val courseType: String? = null,
    val classOrSemester: String = "",
    val about: String = ""
)

class ProfileViewModel : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state = _state.asStateFlow()

    fun isFieldMissing(value: String?): Boolean {
        return value.isNullOrBlank()
    }

    fun updateSchoolCollegeName(name: String) {
        _state.update { it.copy(schoolCollegeName = name) }
    }

    fun updateStudyLevel(level: String) {
        _state.update { 
            it.copy(
                studyLevel = level,
                courseType = null,
                classOrSemester = ""
            ) 
        }
    }

    fun updateCourseType(type: String) {
        _state.update { 
            it.copy(
                courseType = type,
                classOrSemester = ""
            ) 
        }
    }

    fun updateClassOrSemester(value: String) {
        _state.update { it.copy(classOrSemester = value) }
    }

    fun updateAbout(about: String) {
        _state.update { it.copy(about = about) }
    }
}
