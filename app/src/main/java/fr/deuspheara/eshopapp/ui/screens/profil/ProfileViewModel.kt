package fr.deuspheara.eshopapp.ui.screens.profil

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.eshopapp.core.model.auth.UserFullModel
import fr.deuspheara.eshopapp.domain.usecases.auth.GetUserUseCase
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
 * fr.deuspheara.eshopapp.ui.screens.profil.ProfileViewModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Profile view model for [ProfilScreen]
 *
 */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel() {
    private companion object {
        const val TAG = "ProfileViewModel"
    }

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading(false))
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _uiState.value = ProfileUiState.Loading(true)
        getUserUseCase()
            .map<UserFullModel, ProfileUiState>{ user ->
                ProfileUiState.Success(user)
            }.catch { e ->
                _uiState.value = ProfileUiState.Error(e.message ?: "An error occurred")
            }.let {
                _uiState.emitAll(it)
            }
    }


}