package fr.deuspheara.eshopapp.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.deuspheara.eshopapp.core.model.auth.Token
import fr.deuspheara.eshopapp.core.model.products.ProductLightModel
import fr.deuspheara.eshopapp.domain.usecases.auth.AuthenticateUseCase
import fr.deuspheara.eshopapp.domain.usecases.shop.GetProductsUseCase
import fr.deuspheara.eshopapp.domain.usecases.shop.GetRecentLocalProductsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.home.HomeViewModel
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Home view model
 *
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    getProductUseCase: GetProductsUseCase,
    private val authenticateUseCase: AuthenticateUseCase,
    private val getRecentLocalProductsUseCase: GetRecentLocalProductsUseCase,
) : ViewModel() {
    private companion object {
        const val TAG = "HomeViewModel"
    }

    private val _uiState = MutableStateFlow<HomeUiState>(HomeUiState.Loading(false))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val _searchText = MutableStateFlow(HomeUiState.SearchText(""))
    val searchText: StateFlow<HomeUiState.SearchText> = _searchText.asStateFlow()

    val allProducts: Flow<PagingData<ProductLightModel>> =
        getProductUseCase(category = "Montre").cachedIn(viewModelScope)

    val recentLocalProducts: Flow<PagingData<ProductLightModel>> =
        getRecentLocalProductsUseCase().cachedIn(viewModelScope)




    val filteredProducts: Flow<PagingData<ProductLightModel>> =
        combine(_searchText, allProducts) { searchText, allProducts ->
            //TODO: make it work for all products,
            // fetch product that are not yet paginated with custom filter
            allProducts.filter { product ->
                product.name?.contains(searchText.searchText, ignoreCase = true) ?: false
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


    fun onSearchTextChange(searchText: String) = viewModelScope.launch {
        _searchText.value = HomeUiState.SearchText(searchText)
    }


    init {
        authenticate()
    }

    fun fetchSearchProducts(s: String) {
        val tmpListRandomProducts = PagingData.empty<ProductLightModel>()


    }

    private fun authenticate() = viewModelScope.launch {
        _uiState.value = HomeUiState.Loading(true)

        authenticateUseCase(Token("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJqd3QtYXVkaWVuY2UiLCJpc3MiOiJrdG9yIHNhbXBsZSBhcHAiLCJleHAiOjE3Mzc4MTgyNzUsInVzZXJJZCI6IjY1YWFhMGUxYWUzMWVkMDE1NjA1MTIwNCJ9.q42ZMQQsXH2IdmV-muFclHlLoOs3vbwrUn6UtNap6Ro"))
            .map<Boolean, HomeUiState> { isAuthenticated ->
                Log.d(
                    TAG,
                    "isAuthenticated: ${if (isAuthenticated) "user is authenticated" else "user is not authenticated"}"
                )
                HomeUiState.Authenticated(isAuthenticated)
            }.catch { e ->
                emit(HomeUiState.Error(e.message ?: "Unknown error"))
            }.let {
                _uiState.emitAll(it)
            }
    }

}