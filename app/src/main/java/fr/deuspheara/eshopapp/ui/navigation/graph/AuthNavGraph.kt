package fr.deuspheara.eshopapp.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.navigation
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination.Companion.composable
import fr.deuspheara.eshopapp.ui.navigation.ShopAppRoutable.Companion.navigate
import fr.deuspheara.eshopapp.ui.screens.auth.information.InformationScreen
import fr.deuspheara.eshopapp.ui.screens.auth.signin.SignInScreen
import fr.deuspheara.eshopapp.ui.screens.auth.signup.SignUpScreen

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.navigation.graph.AuthNavGraph
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Auth nav graph
 *
 */
fun NavGraphBuilder.addAuthNavGraph(
    startDestination: ShopAppDestination = ShopAppDestination.SignInDestination,
    navController: NavHostController,
) {
    navigation(
        route = "Auth",
        startDestination = startDestination.route,
    ) {
        composable(ShopAppDestination.SignInDestination) {
            SignInScreen(
                onNavigateBack = {
                    navController.navigate(ShopAppDestination.MainDestination)
                },
                onNavigateSignUp = {
                    navController.navigate(ShopAppDestination.SignUpDestination)
                }
            )
        }
        composable(ShopAppDestination.SignUpDestination) {
            SignUpScreen(
                onNavigateBack = {
                    navController.navigateUp()
                },
                onNavigateToInformation = {
                    navController.navigate(ShopAppDestination.InformationDestination)
                }
            )
        }

        composable(ShopAppDestination.InformationDestination) {
            InformationScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}