package fr.deuspheara.eshopapp.ui.screens.auth.information

import fr.deuspheara.eshopapp.core.model.auth.Email
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.auth.information.InformationUiState
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Information ui state
 *
 */
sealed interface InformationUiState {

    data class FormInputState(
        val email: Email,
        val firstName: String,
        val lastName: String,
        val zipCode: String,
        val address: String,
        val city: String,
        val country: String,
    ) : InformationUiState

    data class FormInputError(
        val isEmailError: Boolean,

    ) : InformationUiState {
        fun isError() = isEmailError
    }

    @JvmInline
    value class Loading(val isLoading: Boolean) : InformationUiState

    data class Success(val message: TokenResponse) : InformationUiState

    @JvmInline
    value class Error(val message: String) : InformationUiState
}