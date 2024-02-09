package fr.deuspheara.eshopapp.ui.screens.cart

import fr.deuspheara.eshopapp.core.model.products.ProductCartModel

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.cart.CartUiState
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Cart ui state for [CartScreen]
 *
 */
sealed interface CartUiState {
    @JvmInline
    value class Loading(val isLoading: Boolean) : CartUiState

    @JvmInline
    value class Error(val message: String) : CartUiState

    data class Success(val products: List<ProductCartModel>) : CartUiState
}