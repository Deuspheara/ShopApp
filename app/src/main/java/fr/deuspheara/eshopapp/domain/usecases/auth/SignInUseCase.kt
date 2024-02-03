package fr.deuspheara.eshopapp.domain.usecases.auth

import android.util.Log
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.auth.SignInUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Sign in use case
 *
 */
class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    private companion object {
        const val TAG = "SignInUseCase"
    }

    suspend operator fun invoke(username: Username, password: Password): Flow<TokenResponse> =
        flow {
            emit(authRepository.signIn(username, password))
        }
            .catch {
                Log.e(TAG, "invoke: $it")
                throw it
            }
}