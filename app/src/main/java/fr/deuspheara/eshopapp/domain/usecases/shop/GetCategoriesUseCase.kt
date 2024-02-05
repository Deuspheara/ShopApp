package fr.deuspheara.eshopapp.domain.usecases.shop

import fr.deuspheara.eshopapp.core.model.products.Category
import fr.deuspheara.eshopapp.data.repository.shop.ShopRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.domain.usecases.shop.GetCategoriesUseCase
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Get categories use case
 *
 */
class GetCategoriesUseCase @Inject constructor(
    private val shopRepository: ShopRepository
) {
    private companion object {
        private const val TAG = "GetCategoriesUseCase"
    }

    operator fun invoke(): Flow<List<Category>> = flow {
        emit(shopRepository.getCategories().map(::Category))
    }
}