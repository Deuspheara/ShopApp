package fr.deuspheara.eshopapp.ui.screens.cart

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.core.model.products.Quantity
import fr.deuspheara.eshopapp.domain.usecases.shop.DecrementCartItemQuantityUseCase
import fr.deuspheara.eshopapp.domain.usecases.shop.GetCartProductsUseCase
import fr.deuspheara.eshopapp.domain.usecases.shop.IncrementCartItemQuantityUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.cart.CartViewModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Cart view model
 *
 */
@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartProductsUseCase: GetCartProductsUseCase,
    private val incrementCartItemQuantityUseCase: IncrementCartItemQuantityUseCase,
    private val decrementCartItemQuantityUseCase: DecrementCartItemQuantityUseCase
): ViewModel() {
    private companion object {
        const val TAG = "CartViewModel"
    }

    private val _uiState = MutableStateFlow<CartUiState>(CartUiState.Loading(false))
    val uiState: StateFlow<CartUiState> = _uiState.asStateFlow()

    private val _cartProducts = MutableStateFlow(CartUiState.Success(emptyList()))


    init {
        getCartProducts()
    }

    private fun getCartProducts() = viewModelScope.launch {
        _uiState.value = CartUiState.Loading(true)
        getCartProductsUseCase()
            .map { products ->
                if (products.isEmpty()) {
                    CartUiState.Error("No products in cart")
                } else {
                    _cartProducts.value = CartUiState.Success(products)
                    CartUiState.Success(products)
                }
            }.catch { e ->
                CartUiState.Error(e.message ?: "An error occurred")
            }.let{
                _uiState.emitAll(it)
            }
    }

    fun incrementCartItemQuantity(productId: Identifier) = viewModelScope.launch {
        incrementCartItemQuantityUseCase(productId)
            .map<Int, CartUiState>  { quantity ->
                CartUiState.Success(_cartProducts.value.products.map { product ->
                    if (product.id == productId) {
                        product.copy(quantity = Quantity(quantity))
                    } else {
                        product
                    }
                }).also {
                    _cartProducts.value = it
                    Log.d(TAG, "incrementCartItemQuantity: $it")
                }
            }.catch { e ->
                CartUiState.Error(e.message ?: "An error occurred")
            }.let {
                _uiState.emitAll(it)
            }
    }

    fun decrementCartItemQuantity(productId: Identifier) = viewModelScope.launch {
        decrementCartItemQuantityUseCase(productId)
            .map<Int, CartUiState> { quantity ->
                CartUiState.Success(_cartProducts.value.products.map { product ->
                    if (product.id == productId) {
                        product.copy(quantity = Quantity(quantity))
                    } else {
                        product
                    }
                })
            }.catch { e ->
                CartUiState.Error(e.message ?: "An error occurred")
            }.let {
                _uiState.emitAll(it)
            }
    }



}