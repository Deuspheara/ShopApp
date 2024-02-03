package fr.deuspheara.eshopapp.ui.screens.product.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.eshopapp.core.model.products.ProductFullModel
import fr.deuspheara.eshopapp.domain.usecases.shop.GetProductsByIdsUseCase
import fr.deuspheara.eshopapp.domain.usecases.shop.GetRecentLocalProductsUseCase
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.product.detail.ProductDetailViewModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Product detail view model
 *
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductsByIdsUseCase: GetProductsByIdsUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private companion object {
        const val TAG = "ProductDetailViewModel"
    }

    private val _uiState =
        MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading(false))
    val uiState = _uiState.asStateFlow()

    private val idProduct : String = savedStateHandle.get<String>(ShopAppDestination.ProductDetailDestination.ARGUMENT_PRODUCT_ID) ?: ""

    init {
        fetchDetailedProduct()
    }

    private fun fetchDetailedProduct() = viewModelScope.launch {
        _uiState.value = ProductDetailUiState.Loading(true)
        getProductsByIdsUseCase(idProduct)
            .map{ products ->
                if (products.isNullOrEmpty()) {
                    ProductDetailUiState.Error("No product found")
                } else {
                    ProductDetailUiState.Success(products.first())
                }
            }.catch { e ->
                Log.e(TAG, "Error while fetching product detail", e)
                emit(ProductDetailUiState.Error(e.message ?: "Unknown error"))
            }.let {
                _uiState.emitAll(it)
            }

    }
}