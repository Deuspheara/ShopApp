package fr.deuspheara.eshopapp.data.repository.auth

import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.Token
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.data.datasource.auth.remote.AuthDataSource
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.repository.auth.AuthRepositoryImpl
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Auth repository implementation
 *
 * @see AuthRepository
 */
class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: AuthDataSource,
) : AuthRepository {
    override suspend fun signIn(username: Username, password: Password): TokenResponse {
        //TODO: save token in datastore
        return authDataSource.signIn(username, password)
    }

    override suspend fun signUp(username: Username, password: Password): TokenResponse {
        //TODO: save token in datastore
        return authDataSource.signUp(username, password)
    }

    override suspend fun authenticate(token: Token): Boolean {
        return authDataSource.authenticate(token)
    }
}