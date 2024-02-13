package fr.deuspheara.eshopapp.data.repository.auth

import androidx.datastore.preferences.core.stringPreferencesKey
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.UserFullModel
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
    private companion object {
        private const val TAG = "AuthRepositoryImpl"
        val TOKEN = stringPreferencesKey("token")
    }
    override suspend fun signIn(username: Username, password: Password): TokenResponse {
        return authDataSource.signIn(username, password).also {
            authDataSource.editData(TOKEN, it.token)
        }
    }

    override suspend fun signUp(username: Username, password: Password): TokenResponse {
        return authDataSource.signUp(username, password).also {
            authDataSource.editData(TOKEN, it.token)
        }
    }

    override suspend fun authenticate(): Boolean {
        return authDataSource.authenticate(
            authDataSource.loadData(TOKEN, "")
        )
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
        return authDataSource.updateUser(
            authDataSource.loadData(TOKEN, ""),
            email,
            zipCode,
            address,
            city,
            country,
            firstName,
            lastName
        )
    }

    override suspend fun getUser(): UserFullModel {
        return authDataSource.getUser(
            authDataSource.loadData(TOKEN, "")
        ).let(::UserFullModel)
    }
}