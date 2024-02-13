package fr.deuspheara.eshopapp.domain.usecases.shop

import android.util.Log
import fr.deuspheara.eshopapp.core.model.products.ProductLightModel
import fr.deuspheara.eshopapp.data.network.model.shop.Specification
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.shop.CreateProductUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Create product use case
 *
 */
class CreateProductUseCase @Inject constructor(
    private val shopRepository: ShopRepository
){
    private companion object {
        private const val TAG = "CreateProductUseCase"
    }

    suspend operator fun invoke(
        name: String,
        author: String,
        price: Double,
        promotionPrice: Double,
        description: String,
        currency: String,
        brand: String,
        category: String,
        availability: Boolean,
        stockQuantity: Int,
        images: List<String>,
        specifications: List<Specification>,
        rating: Double,
        reviewsCount: Int
    ) : Flow<ProductLightModel> = flow {
        emit(
            shopRepository.createProduct(
                name,
                author,
                price,
                promotionPrice,
                description,
                currency,
                brand,
                category,
                availability,
                stockQuantity,
                images,
                specifications,
                rating,
                reviewsCount
            )
        )
    }.catch { e ->
        Log.e(TAG, "invoke: ", e)
        throw e
    }
}