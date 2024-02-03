package fr.deuspheara.eshopapp.ui.screens.auth.signin

import fr.deuspheara.eshopapp.core.model.Consumable
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.auth.signin.SignInUiState
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Sign in ui state
 *
 */
sealed interface SignInUiState {

    data class FormInputState(
        val username: Username,
        val password: Password
    ) : SignInUiState

    data class FormInputError(
        val isUsernameError: Boolean,
        val isPasswordError: Boolean
    ) : SignInUiState {
        fun isError() = isUsernameError || isPasswordError
    }

    @JvmInline
    value class Loading(val isLoading: Boolean) : SignInUiState

    data class SignInSuccess(val tokenResponse: TokenResponse) : SignInUiState, Consumable()

    @JvmInline
    value class SignInError(val message: String) : SignInUiState
}