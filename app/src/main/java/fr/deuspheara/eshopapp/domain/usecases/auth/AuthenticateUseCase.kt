package fr.deuspheara.eshopapp.domain.usecases.auth

import android.util.Log
import fr.deuspheara.eshopapp.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.auth.AuthenticateUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Authenticate use case
 *
 */
class AuthenticateUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    private companion object {
        const val TAG = "AuthenticateUseCase"
    }

    suspend operator fun invoke(): Flow<Boolean> = flow {
        emit(authRepository.authenticate())
    }.catch { e ->
        Log.e(TAG, "invoke: ", e)
        throw e
    }
}