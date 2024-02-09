package fr.deuspheara.eshopapp.domain.usecases.auth

import android.util.Log
import fr.deuspheara.eshopapp.core.model.auth.UserFullModel
import fr.deuspheara.eshopapp.data.repository.auth.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.auth.GetUserUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * This use case is used to get the user
 *
 */
class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    private companion object {
        private const val TAG = "GetUserUseCase"
    }

    suspend operator fun invoke(): Flow<UserFullModel> = flow {
        emit(authRepository.getUser())
    }.catch {
        Log.e(TAG, "Error while getting user", it)
        throw it
    }
}