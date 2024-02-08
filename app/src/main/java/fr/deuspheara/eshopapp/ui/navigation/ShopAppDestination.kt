package fr.deuspheara.eshopapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import fr.deuspheara.eshopapp.R

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Navigation destinations
 *
 */

sealed class ShopAppDestination(
    @StringRes val title: Int,
    val showTopAppBar: Boolean = true,
    val navigationIcon: Int? = null,
    val arguments: List<NamedNavArgument> = emptyList(),
    val deepLinks: List<NavDeepLink> = emptyList(),
) : ShopAppRoutable {
    companion object {
        fun NavGraphBuilder.composable(
            destination: ShopAppDestination,
            content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit,
        ) {
            this.composable(
                route = destination.route,
                deepLinks = destination.deepLinks,
                arguments = destination.arguments,
                content = content,
            )
        }
    }

    data object MainDestination : ShopAppDestination(
        title = R.string.app_name,
        showTopAppBar = false,
    ) {
        override val route: String = Destinations.MAIN
    }

    data object AuthDestination : ShopAppDestination(
        title = R.string.auth_title,
    ) {
        override val route: String = Destinations.AUTH
    }

    data object InformationDestination : ShopAppDestination(
        title = R.string.information_title,
    ) {
        override val route: String = Destinations.INFORMATION
    }

    data object HomeDestination : ShopAppDestination(
        title = R.string.home_title,
    ) {
        override val route: String = Destinations.HOME
    }

    class ProductDetailDestination(val productId: String?) : ShopAppRoutable {
        override val route: String
            get() = "${ROUTING_PREFIX}${if (productId != null) "?${ARGUMENT_PRODUCT_ID}=${productId}" else ""}"

        companion object : ShopAppDestination(
            title = R.string.product_detail_title,
            arguments = listOf(
                navArgument(ProductDetailDestination.ARGUMENT_PRODUCT_ID) {
                    type = NavType.StringType
                    nullable = false
                }
            )
        ) {
            override val route: String
                get() = "${ROUTING_PREFIX}?${ARGUMENT_PRODUCT_ID}={${ARGUMENT_PRODUCT_ID}}"

            const val ROUTING_PREFIX = "product_detail/"
            const val ARGUMENT_PRODUCT_ID = "productId"
        }
    }


    data object SignUpDestination : ShopAppDestination(
        title = R.string.sign_up_title,
    ) {
        override val route: String = Destinations.SIGN_UP
    }

    data object SignInDestination : ShopAppDestination(
        title = R.string.sign_in_title,
    ) {
        override val route: String = Destinations.SIGN_IN
    }

    data object CartDestination : ShopAppDestination(
        title = R.string.cart,
    ) {
        override val route: String = Destinations.CART
    }
}

object Destinations {
    const val INFORMATION = "information"
    const val MAIN = "main"
    const val HOME = "home"
    const val SIGN_UP = "sign_up"
    const val SIGN_IN = "sign_in"
    const val AUTH = "auth"
    const val CART = "cart"
}


sealed interface ShopAppRoutable {
    val route: String

    companion object {
        fun NavController.navigate(
            routable: ShopAppRoutable,
            navOptions: NavOptions? = null
        ) = navigate(routable.route, navOptions)

        fun NavController.navigate(
            routable: ShopAppRoutable,
            builder: NavOptionsBuilder.() -> Unit
        ) = navigate(routable.route, navOptions(builder))
    }
}
