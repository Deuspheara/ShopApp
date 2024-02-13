package fr.deuspheara.eshopapp.data.datasource.auth.remote

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import fr.deuspheara.eshopapp.core.coroutines.DispatcherModule
import fr.deuspheara.eshopapp.core.model.auth.AuthRequest
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.data.api.auth.AuthApi
import fr.deuspheara.eshopapp.data.network.NetworkModule.apiCall
import fr.deuspheara.eshopapp.data.network.NetworkModule.safeUnwrap
import fr.deuspheara.eshopapp.data.network.NetworkModule.safeUnwrapEmptyResponse
import fr.deuspheara.eshopapp.data.network.model.auth.UserRemote
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.datasource.auth.remote.AuthDataSourceImpl
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * This class is used to define the auth data source implementation
 *
 */
class AuthDataSourceImpl @Inject constructor(
    private val authApi: AuthApi,
    private val dataStore: DataStore<Preferences>,
    @DispatcherModule.DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : AuthDataSource {

    private companion object {
        private const val TAG = "AuthDataSourceImpl"

    }

    override suspend fun signIn(username: Username, password: Password): TokenResponse {
        return withContext(ioDispatcher) {
            apiCall {
                authApi.signIn(
                    AuthRequest(
                        username = username.value,
                        password = password.value
                    )
                )
            }.safeUnwrap()
        }
    }

    override suspend fun signUp(username: Username, password: Password): TokenResponse {
        return withContext(ioDispatcher) {
            apiCall {
                authApi.signUp(
                    AuthRequest(
                        username = username.value,
                        password = password.value
                    )
                )
            }
            .safeUnwrap()
        }
    }

    override suspend fun authenticate(token: String): Boolean {
        return withContext(ioDispatcher) {

            val formattedToken = "Bearer ${token}"

            apiCall {
                authApi.authenticate(formattedToken)
            }.safeUnwrapEmptyResponse()
        }
    }

    override suspend fun updateUser(
        token: String,
        email: String?,
        zipCode: String?,
        address: String?,
        city: String?,
        country: String?,
        firstName: String?,
        lastName: String?
    ): TokenResponse {
        return withContext(ioDispatcher) {

            apiCall {
                authApi.updateUser(
                    "Bearer $token",
                    email,
                    lastName,
                    firstName,
                    zipCode,
                    city,
                    country,
                    country
                )
            }.safeUnwrap()
        }
    }

    override suspend fun getUser(token: String): UserRemote {
        return withContext(ioDispatcher) {
            apiCall {
                authApi.getUser("Bearer $token")
            }.safeUnwrap()
        }
    }

    override suspend fun <T> loadData(key: Preferences.Key<T>, defaultValue: T): T {
        return withContext(ioDispatcher) {
            try {
                dataStore.data.map { preferences ->
                    preferences[key] ?: defaultValue
                }.first()
            } catch (e: Exception) {
                Log.e(TAG, "Error while loading data with key: $key", e)
                throw e
            }
        }
    }

    override suspend fun <T> editData(key: Preferences.Key<T>, value: T): Instant? {
        return withContext(ioDispatcher) {
            try {
                dataStore.edit { preferences ->
                    preferences[key] = value
                }
                Instant.now()
            } catch (e: Exception) {
                Log.e(TAG, "Error while editing data with key: $key", e)
                throw e
            }
        }
    }
}