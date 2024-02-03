package fr.deuspheara.eshopapp.data.datasource.auth.remote

import fr.deuspheara.eshopapp.core.coroutines.DispatcherModule
import fr.deuspheara.eshopapp.core.model.auth.AuthRequest
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.Token
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.data.api.auth.AuthApi
import fr.deuspheara.eshopapp.data.network.NetworkModule.apiCall
import fr.deuspheara.eshopapp.data.network.NetworkModule.safeUnwrap
import fr.deuspheara.eshopapp.data.network.NetworkModule.safeUnwrapEmptyResponse
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
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
    @DispatcherModule.DispatcherIO private val ioDispatcher: CoroutineDispatcher,
) : AuthDataSource {

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
            }.safeUnwrap()
        }
    }

    override suspend fun authenticate(token: Token): Boolean {
        return withContext(ioDispatcher) {
            apiCall { authApi.authenticate("Bearer ${token.value}") }.safeUnwrapEmptyResponse()
        }
    }
}