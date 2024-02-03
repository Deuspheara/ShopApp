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
 * fr.deuspheara.eshopapp.domain.usecases.shop.GetRecentLocalProductsUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Get recent local products use case
 *
 */
class GetRecentLocalProductsUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    private companion object {
        private const val TAG = "GetRecentLocalProductsUseCase"
    }

    operator fun invoke() : Flow<PagingData<ProductLightModel>> {
        return shopRepository.getRecentLocalProducts(
            page = 0,
            size = 10
        ).catch { e ->
            Log.e(TAG, "Error while getting recent local products", e)
            throw e
        }
    }
}