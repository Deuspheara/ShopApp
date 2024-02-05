package fr.deuspheara.eshopapp.domain.usecases.shop

import fr.deuspheara.eshopapp.core.model.products.ProductCartModel
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.shop.GetCartProductsUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Get cart products use case
 *
 */
class GetCartProductsUseCase @Inject constructor(
    private val shopRepository: ShopRepository
){
    private companion object {
        private const val TAG = "GetCartProductsUseCase"
    }

    operator fun invoke(): Flow<List<ProductCartModel>> = flow {
        emit(shopRepository.getCart())
    }.catch { e ->
        e.printStackTrace()
        throw e
    }
}