package com.resultbookpro.app.presentation.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ProfileState(
    val fullName: String = "Farhan Haider",
    val email: String = "farhanhaider@gmail.com",
    val profilePicUrl: String? = null,
    val schoolCollegeName: String = "St. Xavier's High School",
    val studyLevel: String = "Higher Secondary (11–12)", // Nursery/KG, School (1–10), Higher Secondary (11–12), College / University
    val courseType: String? = "Science",
    val classOrSemester: String = "12th",
    val about: String = "I am a dedicated student pursuing Science stream with a keen interest in Mathematics and Physics."
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
                classOrSemester = "" // Reset semester/class when course type changes
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
