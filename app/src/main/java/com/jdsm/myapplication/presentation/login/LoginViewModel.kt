import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.jdsm.myapplication.presentation.login.LoginEvent
import com.jdsm.myapplication.presentation.login.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EmailChange -> onEmailChange(event.email)
            is LoginEvent.PasswordChange -> onPasswordChange(event.password)
            LoginEvent.IsPasswordVisibleChange -> onIsPasswordVisibleChange()
            LoginEvent.Login -> onLogin()
        }
    }

    private fun onLogin() {
        viewModelScope.launch {
            _state.update { state ->
                val isEmpty = state.email.isEmpty() || state.password.isEmpty()
                val isValid = state.email == "info@koalit.dev" && state.password == "koalit123"

                state.copy(
                    successfulLogin = isValid && !isEmpty,
                    hasEmpty = isEmpty,
                    hasError = !isValid && !isEmpty
                )
            }
        }
    }

    private fun onEmailChange(email: String) {
        _state.update { state ->
            state.copy(
                email = email,
                hasError = false
            )
        }
    }

    private fun onPasswordChange(password: String) {
        _state.update { state ->
            state.copy(
                password = password,
                hasError = false
            )
        }
    }

    private fun onIsPasswordVisibleChange() {
        _state.update { state ->
            state.copy(
                isPasswordVisible = !state.isPasswordVisible
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                LoginViewModel()
            }
        }
    }
}
