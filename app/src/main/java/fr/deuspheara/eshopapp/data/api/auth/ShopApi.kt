package fr.deuspheara.eshopapp.data.api.auth

import fr.deuspheara.eshopapp.data.network.model.ResponseContainer
import fr.deuspheara.eshopapp.data.network.model.shop.ProductRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.data.api.auth.ShopApi
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop api
 *
 */

interface ShopApi {
    @GET("products")
    suspend fun getProducts(
        @Query("id")
        id: String? = null,
        @Query("category")
        category: String? = null,
        @Query("page")
        page: Int? = null,
        @Query("size")
        size: Int? = null
    ): Response<ResponseContainer<ProductRemote>>
}