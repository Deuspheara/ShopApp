package fr.deuspheara.eshopapp.ui.screens.profil

import fr.deuspheara.eshopapp.core.model.auth.UserFullModel

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.profil.ProfileUiState
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Profile ui state
 *
 */
sealed interface ProfileUiState {
    data object Logout : ProfileUiState

    @JvmInline
    value class Loading(val isLoading: Boolean): ProfileUiState
    @JvmInline
    value class Success(val user: UserFullModel): ProfileUiState

    @JvmInline
    value class Error(val message: String): ProfileUiState
}