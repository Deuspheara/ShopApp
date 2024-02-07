package fr.deuspheara.eshopapp.ui.navigation.graph

import androidx.compose.material3.SnackbarHostState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination.Companion.composable
import fr.deuspheara.eshopapp.ui.navigation.ShopAppRoutable.Companion.navigate
import fr.deuspheara.eshopapp.ui.screens.cart.CartScreen
import fr.deuspheara.eshopapp.ui.screens.home.HomeScreen
import fr.deuspheara.eshopapp.ui.screens.product.detail.ProductDetailScreen

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.navigation.graph.MainNavGraph
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Main navigation graph
 *
 */
fun NavGraphBuilder.addMainNavGraph(
    isExpandedScreen: Boolean,
    startDestination: ShopAppDestination = ShopAppDestination.HomeDestination,
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
) {
    navigation(
        route = "Main",
        startDestination = startDestination.route,
    ) {
        composable(ShopAppDestination.HomeDestination) {

            HomeScreen(
                viewModel = hiltViewModel(),
                onNavigateToDetailedProduct = { productId ->
                    navController.navigate(ShopAppDestination.ProductDetailDestination(productId))
                },
                onNavigateToSignIn = {
                    navController.navigate(ShopAppDestination.AuthDestination)
                },
                onNavigateToCart = {
                    navController.navigate(ShopAppDestination.CartDestination)
                },
            )
        }
        composable(ShopAppDestination.ProductDetailDestination) {
            ProductDetailScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = {
                    navController.navigateUp()
                },
            )
        }

        composable(ShopAppDestination.CartDestination) {
            CartScreen(
                viewModel = hiltViewModel(),
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

    }
}