package fr.deuspheara.eshopapp.ui.screens.product.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.core.model.products.Quantity
import fr.deuspheara.eshopapp.domain.usecases.shop.GetCartItemById
import fr.deuspheara.eshopapp.domain.usecases.shop.GetProductsByIdsUseCase
import fr.deuspheara.eshopapp.domain.usecases.shop.IncrementCartItemQuantityUseCase
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
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
    private val incrementCartItemQuantity: IncrementCartItemQuantityUseCase,
    private val getCartItemById: GetCartItemById,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private companion object {
        const val TAG = "ProductDetailViewModel"
    }

    private val _uiState =
        MutableStateFlow<ProductDetailUiState>(ProductDetailUiState.Loading(false))
    val uiState = _uiState.asStateFlow()

    private val idProduct : String = savedStateHandle.get<String>(ShopAppDestination.ProductDetailDestination.ARGUMENT_PRODUCT_ID) ?: ""


    private val _cartQuantity = MutableStateFlow<Quantity>(Quantity(0))
    val cartQuantity = _cartQuantity.asStateFlow()

    init {
        fetchDetailedProduct()
        fetchCartQuantity()
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

    private fun fetchCartQuantity() = viewModelScope.launch {
        getCartItemById(Identifier(idProduct))
            .map {
                it?.quantity ?: Quantity(0)
            }.catch { e ->
                Log.e(TAG, "Error while fetching cart quantity", e)
                emit(Quantity(0))
            }.let {
                _cartQuantity.emitAll(it)
            }
    }

    fun addToCart() = viewModelScope.launch {
        try {
            incrementCartItemQuantity(Identifier(idProduct)).collectLatest {
                _cartQuantity.value = Quantity(_cartQuantity.value.value + 1)
            }
            Log.d(TAG, "addToCart: idProduct: $idProduct, quantity: ${_cartQuantity.value}")
        } catch (e: Exception) {
            Log.e(TAG, "Error while adding product to cart", e)
            _uiState.value = ProductDetailUiState.Error(e.message ?: "Unknown error")
        }
    }
}