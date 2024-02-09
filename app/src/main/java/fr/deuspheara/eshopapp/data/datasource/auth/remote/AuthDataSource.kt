package fr.deuspheara.eshopapp.data.datasource.auth.remote

import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.data.network.model.auth.UserRemote

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
     *
     * @return [Boolean] true if authenticated, false otherwise
     */
    suspend fun authenticate(): Boolean

    /**
     * Update user
     *
     * @param email
     * @param zipCode
     * @param address
     * @param city
     * @param country
     * @param firstName
     * @param lastName
     *
     * @return [TokenResponse] the new token
     */
    suspend fun updateUser(
        email: String? = null,
        zipCode: String? = null,
        address: String? = null,
        city: String? = null,
        country: String? = null,
        firstName: String? = null,
        lastName: String? = null
    ): TokenResponse


    /**
     * Get user
     *
     * @return [UserRemote] the user
     */
    suspend fun getUser(): UserRemote
}