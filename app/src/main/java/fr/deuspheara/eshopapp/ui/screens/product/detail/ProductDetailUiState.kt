package fr.deuspheara.eshopapp.ui.screens.product.detail

import fr.deuspheara.eshopapp.core.model.products.ProductFullModel

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.product.detail.ProductDetailUiState
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Product detail ui state
 *
 */
sealed interface ProductDetailUiState {

    @JvmInline
    value class Loading(val isLoading: Boolean = false) : ProductDetailUiState

    @JvmInline
    value class Error(val message: String) : ProductDetailUiState

    @JvmInline
    value class Success(val product: ProductFullModel) : ProductDetailUiState
}