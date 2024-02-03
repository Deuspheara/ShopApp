package fr.deuspheara.eshopapp.data.repository.auth

import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.Token
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.repository.auth.AuthRepository
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Auth repository
 *
 */
interface AuthRepository {
    /**
     * Sign in
     * @param username
     * @param password
     *
     * @return token a [TokenResponse]
     */
    suspend fun signIn(username: Username, password: Password): TokenResponse

    /**
     * Sign up
     * @param username
     * @param password
     *
     * @return token a [TokenResponse]
     */
    suspend fun signUp(username: Username, password: Password): TokenResponse

    /**
     * Authenticate
     * @param token token
     *
     * @return [Boolean] true if authenticated, false otherwise
     */
    suspend fun authenticate(token: Token): Boolean
}