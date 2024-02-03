package fr.deuspheara.eshopapp.data.api.auth

import fr.deuspheara.eshopapp.core.model.auth.AuthRequest
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.api.auth.AuthApi
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Auth api
 *
 */
interface AuthApi {
    /**
     * Signin
     * @param authRequest
     * @return a boolean of login state
     */
    @POST("signin")
    suspend fun signIn(
        @Body
        authRequest: AuthRequest
    ): Response<TokenResponse>

    /**
     * Signup
     * @param authRequest
     * @return a boolean of register state
     */
    @POST("signup")
    suspend fun signUp(
        @Body
        authRequest: AuthRequest
    ): Response<TokenResponse>

    /**
     * Authenticate
     * @param auth token in Authorization header
     *
     * @return [Boolean] true if authenticated, false otherwise
     */
    @GET("authenticate")
    suspend fun authenticate(
        @Header("Authorization")
        auth: String
    ): Response<Void>

}