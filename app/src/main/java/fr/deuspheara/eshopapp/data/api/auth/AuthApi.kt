package fr.deuspheara.eshopapp.data.api.auth

import fr.deuspheara.eshopapp.core.model.auth.AuthRequest
import fr.deuspheara.eshopapp.core.model.auth.TokenResponse
import fr.deuspheara.eshopapp.data.network.model.auth.UserRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

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

    @POST("user/update")
    suspend fun updateUser(
        @Header("Authorization")
        auth: String? = null,
        @Query("email")
        email: String? = null,
        @Query("last_name")
        lastName: String? = null,
        @Query("first_name")
        firstName: String? = null,
        @Query("zip_code")
        zipCode: String? = null,
        @Query("city")
        city: String? = null,
        @Query("address")
        address: String? = null,
        @Query("country")
        country: String? = null,
    ): Response<TokenResponse>


    @GET("user")
    suspend fun getUser(
        @Header("Authorization")
        auth: String
    ): Response<UserRemote>

}