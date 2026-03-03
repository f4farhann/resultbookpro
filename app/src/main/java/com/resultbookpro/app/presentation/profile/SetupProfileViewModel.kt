package com.resultbookpro.app.presentation.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SetupProfileViewModel : ViewModel() {

    private val _fullNameState = MutableStateFlow("")
    val fullNameState = _fullNameState.asStateFlow()

    private val _schoolNameState = MutableStateFlow("")
    val schoolNameState = _schoolNameState.asStateFlow()

    private val _classNameState = MutableStateFlow("")
    val classNameState = _classNameState.asStateFlow()

    fun onFullNameChange(name: String) {
        _fullNameState.value = name
    }

    fun onSchoolNameChange(school: String) {
        _schoolNameState.value = school
    }

    fun onClassNameChange(className: String) {
        _classNameState.value = className
    }
}