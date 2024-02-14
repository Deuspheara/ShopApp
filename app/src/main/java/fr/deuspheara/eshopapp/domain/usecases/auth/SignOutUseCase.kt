package fr.deuspheara.eshopapp.domain.usecases.auth

import fr.deuspheara.eshopapp.data.repository.auth.AuthRepository
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.auth.SignOutUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Sign out use case
 *
 */
class SignOutUseCase @Inject constructor(
    private val authRepository: AuthRepository
){
    private companion object {
        private const val TAG = "SignOutUseCase"
    }

    suspend operator fun invoke() {
        authRepository.signOut()
    }
}