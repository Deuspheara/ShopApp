package fr.deuspheara.eshopapp.domain.usecases.auth

import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.auth.SignUpUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Sign up use case
 *
 */
class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    private companion object {
        const val TAG = "SignUpUseCase"
    }

    suspend operator fun invoke(username: Username, password: Password): Flow<TokenResponse> =
        flow {
            emit(authRepository.signUp(username, password))
        }
}