package com.abouttime.blindcafe.presentation.login

sealed class LoginState {
    object Uninitialized: LoginState()
    object Loading: LoginState()
    object Success: LoginState()
    object Error: LoginState()
}
