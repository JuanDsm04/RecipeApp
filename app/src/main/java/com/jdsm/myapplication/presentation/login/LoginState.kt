package com.jdsm.myapplication.presentation.login

data class LoginState (
    val email: String = "",
    val password: String = "",
    val isPasswordVisible: Boolean = false,
    val hasError: Boolean = false,
    val hasEmpty: Boolean = false,
    val successfulLogin: Boolean = false
)