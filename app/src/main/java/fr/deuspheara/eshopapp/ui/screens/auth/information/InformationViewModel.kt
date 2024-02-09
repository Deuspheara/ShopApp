package fr.deuspheara.eshopapp.ui.screens.auth.information

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.eshopapp.core.model.auth.Email
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.domain.usecases.auth.UpdateUserUseCase
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
 * fr.deuspheara.eshopapp.ui.screens.auth.information.InformationViewModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Information view model
 *
 */
@HiltViewModel
class InformationViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase
): ViewModel() {
    private companion object {
        private const val TAG = "InformationViewModel"
    }

    private val _uiState = MutableStateFlow<InformationUiState>(InformationUiState.Loading(false))
    val uiState: StateFlow<InformationUiState> = _uiState.asStateFlow()


    private val _formInputState = MutableStateFlow(
        InformationUiState.FormInputState(
            email = Email(""),
            firstName = "",
            lastName = "",
            zipCode = "",
            address = "",
            city = "",
            country = ""
        )
    )

    val formInputState = _formInputState.asStateFlow()

    fun onEmailChanged(email: Email) {
        _formInputState.value = _formInputState.value.copy(email = email)
    }

    fun onFirstNameChanged(firstName: String) {
        _formInputState.value = _formInputState.value.copy(firstName = firstName)
    }

    fun onLastNameChanged(lastName: String) {
        _formInputState.value = _formInputState.value.copy(lastName = lastName)
    }

    fun onZipCodeChanged(zipCode: String) {
        _formInputState.value = _formInputState.value.copy(zipCode = zipCode)
    }

    fun onAddressChanged(address: String) {
        _formInputState.value = _formInputState.value.copy(address = address)
    }

    fun onCityChanged(city: String) {
        _formInputState.value = _formInputState.value.copy(city = city)
    }

    fun onCountryChanged(country: String) {
        _formInputState.value = _formInputState.value.copy(country = country)
    }


    //Update user information
    fun submitForm() = viewModelScope.launch {
        _uiState.value = InformationUiState.Loading(true)
        _formInputState.value.apply {
            val inputError = InformationUiState.FormInputError(
                isEmailError = !email.isValid(),
            )

            if (inputError.isError()) _uiState.emit(
                inputError
            ) else {
                updateUserUseCase(
                    email = email.value,
                    firstName = firstName,
                    lastName = lastName,
                    zipCode = zipCode,
                    address = address,
                    city = city,
                    country = country
                ).map<TokenResponse, InformationUiState> {
                    Log.d(TAG, "submitForm: $it")
                    InformationUiState.Success(it)
                }.catch { e ->
                    Log.e(TAG, "submitForm: ", e)
                    _uiState.value = InformationUiState.Error(e.message ?: "An error occurred")
                }.let { _uiState.emitAll(it) }
            }

        }
    }

}