package fr.deuspheara.eshopapp.ui.screens.profil

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.auth.UserFullModel
import fr.deuspheara.eshopapp.ui.components.bar.top.ShopAppCenteredTopBar
import fr.deuspheara.eshopapp.ui.components.card.CardButton
import fr.deuspheara.eshopapp.ui.components.card.InformationItem
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.profil.ProfilScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
@Composable
fun ProfilScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateToManageProduct: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if(uiState is ProfileUiState.Logout) {
            onNavigateBack()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            ShopAppCenteredTopBar(
                destination = ShopAppDestination.ProfilDestination,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = viewModel::signOut) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_logout),
                            contentDescription = stringResource(id = R.string.logout)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ProfileScreenContent(
            modifier = modifier
                .padding(innerPadding),
            userFullModel = (uiState as? ProfileUiState.Success)?.user,
            onNavigateToManageProduct = onNavigateToManageProduct,
        )
    }
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    userFullModel: UserFullModel? = null,
    onNavigateToManageProduct: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        userFullModel?.apply {
            username?.let {
                InformationItem(
                    icon = R.drawable.ic_user,
                    title = username,
                )
            }
            if (firstName != null || lastName != null) {
                InformationItem(
                    icon = R.drawable.ic_name,
                    title = firstName.orEmpty() + " " + lastName.orEmpty(),
                )
            }
            email?.let {
                InformationItem(
                    icon = R.drawable.ic_email,
                    title = email,
                )
            }

            Text(
                text = stringResource(id = R.string.information_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSurface
            )

            if (zipCode != null || address != null) {
                InformationItem(
                    icon = R.drawable.ic_location,
                    title = zipCode.orEmpty() + " " + address.orEmpty(),
                )
            }

            city?.let { city ->
                InformationItem(
                    icon = R.drawable.ic_city,
                    title = city,
                )
            }

            country?.let { country ->
                InformationItem(
                    icon = R.drawable.ic_country,
                    title = country,
                )
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .padding(16.dp)
                    .height(504.dp),
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
               item {
                    CardButton(
                        icon = R.drawable.ic_edit,
                        title = R.string.edit,
                        subtitle = R.string.edit_subtitle,
                        onClick = {
                            //navigate to edit screen
                        },
                        color = Color.Blue.copy(alpha = 0.2f)
                    )
                }

                item {
                    CardButton(
                        icon = R.drawable.ic_product,
                        title = R.string.manage_products,
                        subtitle = R.string.manage_products_subtitle,
                        onClick = onNavigateToManageProduct,
                        color = Color.Green.copy(alpha = 0.2f)
                    )
                }

                item {
                    CardButton(
                        icon = R.drawable.ic_cart,
                        title = R.string.my_purchases,
                        subtitle = R.string.my_purchases_subtitle,
                        onClick = {
                            //navigate to edit screen
                        },
                        color = Color.Yellow.copy(alpha = 0.2f)
                    )
                }

                item {
                    CardButton(
                        icon = R.drawable.ic_parameter,
                        title = R.string.parameter,
                        subtitle = R.string.parameter_subtitle,
                        onClick = {
                            //navigate to orders screen
                        },
                        color = Color.Red.copy(alpha = 0.2f)
                    )
                }
            }

        }

    }
}

@Composable
@Preview(showBackground = true)
fun ProfilScreenPreview() {
    ProfileScreenContent()
}