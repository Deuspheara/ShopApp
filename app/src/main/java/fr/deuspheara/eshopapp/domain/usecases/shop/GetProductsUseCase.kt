package fr.deuspheara.eshopapp.domain.usecases.shop

import android.util.Log
import androidx.paging.PagingData
import fr.deuspheara.eshopapp.core.model.products.ProductLightModel
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.shop.GetProductsUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
class GetProductsUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    private companion object {
        const val TAG = "GetProductsUseCase"
    }

    operator fun invoke(
        id: String? = null,
        category: String? = null,
        page: Int? = 0,
        size: Int? = 10
    ): Flow<PagingData<ProductLightModel>> {
        return shopRepository.getProducts(
            id = id,
            category = category,
            page = page,
            size = size
        ).catch { e ->
            Log.e(TAG, "invoke: ", e)
        }
    }
}