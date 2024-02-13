package fr.deuspheara.eshopapp.ui.components.bar.bottom

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.bar.bottom.ShopAppBottomBar
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
@Composable
fun ShopAppBottomBar(
    navController: NavController,
    onSelected: (ShopAppDestination) -> Boolean,
    destination: ShopAppDestination = ShopAppDestination.HomeDestination,
    updateNavigationBarColor: (Int) -> Unit = {},
) {

    val navigationBarColor by animateColorAsState(
        targetValue = if (destination in ShopAppDestination.BottomBarItems) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface,
        label = "bottom_color"
    )

    updateNavigationBarColor(navigationBarColor.toArgb())

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.secondaryContainer
    ) {
        ShopAppDestination.BottomBarItems.forEach { item ->
            val isSelected = onSelected(item)
            val tint = animateColorAsState(
                targetValue = if (isSelected) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondaryContainer,
                label = "bar_color"
            )
            NavigationBarItem(
                icon = {
                    item.navigationIcon?.let {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(id = it),
                            contentDescription = null,
                            tint = tint.value
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(item.title),
                        color = tint.value
                    )
                },
                selected = isSelected,
                onClick = {
                    if (!isSelected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                }
            )
        }
    }
}