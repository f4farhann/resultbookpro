package com.resultbookpro.app.presentation.auth.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RegisterViewModel : ViewModel() {

    private val _nameState = MutableStateFlow("")
    val nameState = _nameState.asStateFlow()

    private val _emailState = MutableStateFlow("")
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow("")
    val passwordState = _passwordState.asStateFlow()

    fun onNameChange(name: String) {
        _nameState.value = name
    }

    fun onEmailChange(email: String) {
        _emailState.value = email
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = password
    }
}