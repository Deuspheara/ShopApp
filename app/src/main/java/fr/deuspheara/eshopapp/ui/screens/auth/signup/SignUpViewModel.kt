package fr.deuspheara.eshopapp.ui.screens.auth.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.domain.usecases.auth.SignUpUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.auth.signup.SignUpViewModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase
): ViewModel(){
    private companion object {
        const val TAG = "SignUpViewModel"
    }

    private val _uiState = MutableStateFlow<SignUpUiState>(SignUpUiState.Loading(false))
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _formInputState = MutableStateFlow(
        SignUpUiState.FormInputState(
            username = Username(""),
            password = Password("")
        )
    )

    val formInputState = _formInputState.asStateFlow()

    fun onUsernameChanged(username: Username) {
        _formInputState.value = _formInputState.value.copy(username = username)
    }

    fun onPasswordChanged(password: Password) {
        _formInputState.value = _formInputState.value.copy(password = password)
    }

    fun signUp() = viewModelScope.launch {
        _uiState.value = SignUpUiState.Loading(true)
        _formInputState.value.apply {
            val inputError = SignUpUiState.FormInputError(
                isUsernameError = username.value.isBlank(),
                isPasswordError = !password.isStrong
            )

            if (inputError.isError()) _uiState.emit(
                inputError
            ) else {
                _uiState.emit(SignUpUiState.Loading(true))
                signUpUseCase(username, password)
                    .map<TokenResponse, SignUpUiState> {
                        SignUpUiState.Success(it)
                    }
                    .catch { _uiState.value = SignUpUiState.Error(it.message ?: "An error occurred") }
                    .let { _uiState.emitAll(it) }
            }

        }
    }
}