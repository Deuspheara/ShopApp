package fr.deuspheara.eshopapp.ui.components.bar.top

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.bar.top.ShopAppCenteredTopBar
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Centered top bar
 *
 */
@Composable
fun ShopAppCenteredTopBar(
    modifier: Modifier = Modifier,
    destination: ShopAppDestination,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
    isTitleVisible: Boolean = true,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            if (isTitleVisible) {
                Text(
                    text = stringResource(destination.title),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSecondary,
                )
            }
        },
        navigationIcon = navigationIcon,
        actions = actions,
        modifier = modifier,
        scrollBehavior = scrollBehavior
    )
}
