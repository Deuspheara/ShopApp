package fr.deuspheara.eshopapp.data.datasource.auth.remote

import androidx.datastore.preferences.core.Preferences
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.data.network.model.auth.UserRemote
import java.time.Instant

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
    suspend fun authenticate(token: String): Boolean

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
        token: String,
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
    suspend fun getUser(token: String): UserRemote
    suspend fun <T> loadData(key: Preferences.Key<T>, defaultValue: T): T
    suspend fun <T> editData(key: Preferences.Key<T>, value: T): Instant?
}