package fr.deuspheara.eshopapp.domain.usecases.auth

import android.util.Log
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.auth.UpdateUserUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Update user use case
 *
 */
class UpdateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    private companion object {
        const val TAG = "UpdateUserUseCase"
    }

    suspend operator fun invoke(
        email: String?,
        zipCode: String?,
        address: String?,
        city: String?,
        country: String?,
        firstName: String?,
        lastName: String?
    ) : Flow<TokenResponse> = flow {
        emit(authRepository.updateUser(email, zipCode, address, city, country, firstName, lastName))
    }.catch { e ->
        Log.e(TAG, "invoke: ", e)
        throw e
    }
}