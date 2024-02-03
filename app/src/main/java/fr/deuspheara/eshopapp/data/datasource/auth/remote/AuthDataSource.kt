package fr.deuspheara.eshopapp.data.datasource.auth.remote

import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.Token
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.datasource.auth.remote.AuthDataSource
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * This interface is used to define the auth data source
 *
 * @see AuthDataSourceImpl
 */
interface AuthDataSource {
    /**
     * Signin
     * @param username username
     * @param password password
     *
     * @return [TokenResponse]
     */
    suspend fun signIn(username: Username, password: Password): TokenResponse

    /**
     * Signup
     * @param username username
     * @param password password
     *
     * @return [TokenResponse]
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