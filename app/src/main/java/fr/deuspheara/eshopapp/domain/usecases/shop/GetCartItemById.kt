package fr.deuspheara.eshopapp.domain.usecases.shop

import android.util.Log
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.core.model.products.ProductCartInfoModel
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.shop.GetCartItemById
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Get cart item by id
 *
 */
class GetCartItemById @Inject constructor(
    private val shopRepository: ShopRepository
){
    private companion object {
        private const val TAG = "GetCartItemById"
    }

    suspend operator fun invoke(id: Identifier): Flow<ProductCartInfoModel?> = flow {
        emit(shopRepository.getCartItemById(id))
    }.catch { e ->
        Log.e(TAG, "Error while getting cart item by id", e)
        throw e
    }
}