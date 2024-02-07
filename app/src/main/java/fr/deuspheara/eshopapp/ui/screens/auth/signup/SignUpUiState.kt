package fr.deuspheara.eshopapp.ui.screens.auth.signup

import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.auth.signup.SignUpUiState
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Sign up ui state
 *
 */
sealed interface SignUpUiState {

    data class FormInputState(
        val username: Username,
        val password: Password
    ) : SignUpUiState

    data class FormInputError(
        val isUsernameError: Boolean,
        val isPasswordError: Boolean
    ) : SignUpUiState {
        fun isError() = isUsernameError || isPasswordError
    }

    @JvmInline
    value class Loading(val isLoading: Boolean): SignUpUiState

    @JvmInline
    value class Success(val token: TokenResponse): SignUpUiState

    @JvmInline
    value class Error(val message: String): SignUpUiState
}