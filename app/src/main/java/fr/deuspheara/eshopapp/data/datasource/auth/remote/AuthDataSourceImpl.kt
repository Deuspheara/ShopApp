package fr.deuspheara.eshopapp.data.datasource.auth.remote

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
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
        val TOKEN = stringPreferencesKey("token")
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
            .also {
                editData(TOKEN, it.token)
            }
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
            .also {
                editData(TOKEN, it.token)
            }
        }
    }

    override suspend fun authenticate(): Boolean {
        return withContext(ioDispatcher) {
            val tokenValue = loadData(TOKEN, "")
            val formattedToken = "Bearer $tokenValue"

            apiCall {
                authApi.authenticate(formattedToken)
            }.safeUnwrapEmptyResponse()
        }
    }

    override suspend fun updateUser(
        email: String?,
        zipCode: String?,
        address: String?,
        city: String?,
        country: String?,
        firstName: String?,
        lastName: String?
    ): TokenResponse {
        return withContext(ioDispatcher) {
            val tokenValue = loadData(TOKEN, "")

            apiCall {
                authApi.updateUser(
                    "Bearer $tokenValue",
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

    override suspend fun getUser(): UserRemote {
        return withContext(ioDispatcher) {
            val tokenValue = loadData(TOKEN, "")

            apiCall {
                authApi.getUser("Bearer $tokenValue")
            }.safeUnwrap()
        }
    }

    private suspend fun <T> loadData(key: Preferences.Key<T>, defaultValue: T): T {
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

    private suspend fun <T> editData(key: Preferences.Key<T>, value: T): Instant? {
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