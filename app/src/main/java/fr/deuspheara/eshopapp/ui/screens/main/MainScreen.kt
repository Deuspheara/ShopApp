package fr.deuspheara.eshopapp.ui.screens.main

import android.annotation.SuppressLint
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
import fr.deuspheara.eshopapp.ui.navigation.graph.addAuthNavGraph
import fr.deuspheara.eshopapp.ui.navigation.graph.addMainNavGraph

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.main.MainScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Main screen
 *
 */
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    navController: NavHostController = rememberNavController(),
) {
    val snackbarHostState = remember { SnackbarHostState() }

    NavHost(
        navController = navController,
        startDestination = ShopAppDestination.MainDestination.route,
    ) {
        addMainNavGraph(
            startDestination = ShopAppDestination.HomeDestination,
            isExpandedScreen = true,
            snackbarHostState = snackbarHostState,
            navController = navController,
        )
        addAuthNavGraph(
            startDestination = ShopAppDestination.SignInDestination,
            navController = navController,
        )
    }

}
