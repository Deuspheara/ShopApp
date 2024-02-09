package fr.deuspheara.eshopapp.ui.navigation.graph

import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination.Companion.composable
import fr.deuspheara.eshopapp.ui.navigation.ShopAppRoutable.Companion.navigate
import fr.deuspheara.eshopapp.ui.screens.cart.CartScreen
import fr.deuspheara.eshopapp.ui.screens.home.HomeScreen
import fr.deuspheara.eshopapp.ui.screens.product.detail.ProductDetailScreen
import fr.deuspheara.eshopapp.ui.screens.profil.ProfilScreen

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
                onNavigateToDetailedProduct = { productId ->
                    navController.navigate(ShopAppDestination.ProductDetailDestination(productId))
                },
                onNavigateToSignIn = {
                    navController.navigate(ShopAppDestination.AuthDestination)
                },
                onNavigateToCart = {
                    navController.navigate(ShopAppDestination.CartDestination)
                },
                onNavigateToProfile = {
                    navController.navigate(ShopAppDestination.ProfilDestination)
                },
            )
        }
        composable(ShopAppDestination.ProductDetailDestination) {
            ProductDetailScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
            )
        }

        composable(ShopAppDestination.CartDestination) {
            CartScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

        composable(ShopAppDestination.ProfilDestination) {
            ProfilScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }

    }
}