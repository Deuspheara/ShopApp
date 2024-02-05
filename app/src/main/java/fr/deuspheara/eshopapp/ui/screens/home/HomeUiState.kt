package fr.deuspheara.eshopapp.ui.screens.home

import androidx.paging.PagingData
import fr.deuspheara.eshopapp.core.model.products.Category
import fr.deuspheara.eshopapp.core.model.products.ProductLightModel

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.home.HomeUiState
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Home ui state of [HomeScreen]
 *
 */
sealed interface HomeUiState {

    @JvmInline
    value class Loading(val isLoading: Boolean = false) : HomeUiState

    @JvmInline
    value class Error(val message: String) : HomeUiState

    @JvmInline
    value class SearchSuccess(val filteredProducts: PagingData<ProductLightModel>) : HomeUiState

    @JvmInline
    value class SearchText(val searchText: String) : HomeUiState

    @JvmInline
    value class Authenticated(val isAuthenticated: Boolean) : HomeUiState

    @JvmInline
    value class Categories(val categories: List<Category>) : HomeUiState

}