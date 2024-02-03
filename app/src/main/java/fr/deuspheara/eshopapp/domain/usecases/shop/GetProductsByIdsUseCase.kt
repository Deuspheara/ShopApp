package fr.deuspheara.eshopapp.domain.usecases.shop

import android.util.Log
import fr.deuspheara.eshopapp.core.model.products.ProductFullModel
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.shop.GetProductsByIds
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Get products by ids
 *
 */
class GetProductsByIdsUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    private companion object {
        private const val TAG = "GetProductsByIdsUseCase"
    }

    suspend operator fun invoke(vararg ids: String): Flow<List<ProductFullModel>?> = flow {
        emit(shopRepository.getProductByIds(*ids))
    }.catch { e ->
        Log.e(TAG, "Error while getting products by ids: ${ids.joinToString()}", e)
        throw e
    }
}