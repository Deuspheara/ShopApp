package fr.deuspheara.eshopapp.domain.usecases.shop

import android.util.Log
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.shop.DecrementCartItemQuantityUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Decrement cart item quantity use case
 *
 */
class DecrementCartItemQuantityUseCase @Inject constructor(
    private val repository: ShopRepository
) {
    private companion object {
        const val TAG = "DecrementCartItemQuantityUseCase"
    }

    suspend operator fun invoke(productId: Identifier): Flow<Int> = flow {
        emit(repository.decrementCartItemQuantityById(productId))
    }.catch { e ->
        Log.e(TAG, "Error decrementing cart item quantity", e)
        throw e
    }
}