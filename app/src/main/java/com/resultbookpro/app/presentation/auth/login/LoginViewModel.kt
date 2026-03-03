package com.resultbookpro.app.presentation.auth.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _emailState = MutableStateFlow("")
    val emailState = _emailState.asStateFlow()

    private val _passwordState = MutableStateFlow("")
    val passwordState = _passwordState.asStateFlow()

    fun onEmailChange(email: String) {
        _emailState.value = email
    }

    fun onPasswordChange(password: String) {
        _passwordState.value = password
    }
}