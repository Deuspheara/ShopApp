package fr.deuspheara.eshopapp.ui.screens.auth.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.domain.usecases.auth.SignInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.auth.signin.SignInViewModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Sign in view model
 *
 */
@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase
) : ViewModel() {

    private companion object {
        const val TAG = "SignInViewModel"
    }

    private val _uiState: MutableStateFlow<SignInUiState> =
        MutableStateFlow(SignInUiState.Loading(false))
    val uiState = _uiState.asStateFlow()

    private val _formInputState = MutableStateFlow(
        SignInUiState.FormInputState(
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

    fun signIn() = viewModelScope.launch {
        Log.d(TAG, "signIn: ")
        _formInputState.value.apply {
            val inputError = SignInUiState.FormInputError(
                isUsernameError = username.value.isBlank(),
                isPasswordError = !password.isStrong
            )

            if (inputError.isError()) _uiState.emit(
                inputError
            ) else {
                _uiState.emit(SignInUiState.Loading(true))
                signInUseCase(username, password)
                    .map<TokenResponse, SignInUiState> {
                        SignInUiState.SignInSuccess(it)
                    }.catch {
                        Log.e(TAG, "signIn: $it")
                        emit(SignInUiState.SignInError(it.message ?: "Unknown error"))
                    }.let {
                        _uiState.emitAll(it)
                    }
            }
        }


    }

}