package fr.deuspheara.eshopapp.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import fr.deuspheara.eshopapp.BuildConfig
import fr.deuspheara.eshopapp.data.api.auth.AuthApi
import fr.deuspheara.eshopapp.data.api.auth.ShopApi
import fr.deuspheara.eshopapp.data.network.exception.ApiException
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Singleton

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.network.NetworkModule
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Network module
 *
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    /**
     * Provides the OkHttpClient object.
     *
     * @return The [OkHttpClient] object
     */
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            }
        }.build()
    }

    /**
     * Provides the Gson object.
     *
     * @return The [Gson] object
     */
    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        return gsonBuilder.create()
    }

    /**
     * Provides the Retrofit object.
     *
     * @param okHttpClient The [OkHttpClient] object used to instantiate the Retrofit object
     * @param gson The [Gson] object used to instantiate the Retrofit object
     *
     * @return The [Retrofit] object
     */
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.SHOP_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    fun provideShopApi(retrofit: Retrofit): ShopApi {
        return retrofit.create(ShopApi::class.java)
    }

    @Throws(ApiException::class)
    fun <Remote> Response<Remote>.safeUnwrap(): Remote {
        return if (this.isSuccessful) {
            this.body() ?: throw ApiException(
                code = this.code(),
                error = null
            )
        } else {
            val error = this.errorBody()?.string()
            throw ApiException(
                code = this.code(),
                error = error
            )
        }
    }

    @Throws(ApiException::class)
    fun Response<Void>.safeUnwrapEmptyResponse(): Boolean {
        return if (this.code() == 200) {
            true
        } else if (this.code() == 401) {
            false
        } else {
            val error = this.errorBody()?.string()
            throw ApiException(
                code = this.code(),
                error = error
            )
        }
    }

    @Throws(ApiException::class)
    inline fun <Remote> apiCall(block: () -> Response<Remote>): Response<Remote> {
        return runCatching { block() }
            .getOrElse {
                when (it) {
                    is ConnectException, is SocketTimeoutException -> throw ApiException(
                        code = 0,
                        error = "Impossible to connect to the server"
                    )

                    else -> throw it
                }
            }
    }

}