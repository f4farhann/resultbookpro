package com.resultbookpro.app.presentation.category

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CategoryViewModel : ViewModel() {
    private val _selectedStudyLevel = MutableStateFlow("")
    val selectedStudyLevel = _selectedStudyLevel.asStateFlow()

    fun selectStudyLevel(level: String) {
        _selectedStudyLevel.value = level
    }
}
