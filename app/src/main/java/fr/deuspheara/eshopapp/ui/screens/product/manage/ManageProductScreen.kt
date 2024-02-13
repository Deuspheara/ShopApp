package fr.deuspheara.eshopapp.ui.screens.product.manage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.ui.components.bar.top.ShopAppCenteredTopBar
import fr.deuspheara.eshopapp.ui.components.button.ActionButton
import fr.deuspheara.eshopapp.ui.components.modal.ShopAppModal
import fr.deuspheara.eshopapp.ui.components.text.InputField
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.product.manage.ManageProductScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
@Composable
fun ManageProductScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    var showModals by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ShopAppCenteredTopBar(
                destination = ShopAppDestination.ManageProductDestination,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            modifier = Modifier.height(24.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showModals = true
                    }) {
                        Icon(
                            modifier = Modifier.height(24.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ManageProductScreenContent(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
        )
    }

    if (showModals) {
        ShopAppModal(
            onDismissRequest = { showModals = false },
            title = R.string.add_product,
            sheetState = rememberModalBottomSheetState(
                skipPartiallyExpanded = true,
                confirmValueChange = { showModals }
            )
        ) {
            Column{
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = R.string.product_name,
                    value = "",
                    onValueChange = {}
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = R.string.product_price,
                    value = "",
                    onValueChange = {}
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = R.string.product_description,
                    value = "",
                    onValueChange = {}
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = R.string.product_category,
                    value = "",
                    onValueChange = {},
                    maxLines = 4
                )
                InputField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    label = R.string.product_brand,
                    value = "",
                    onValueChange = {},
                    maxLines = 4
                )
                InputField(
                    modifier = Modifier.fillMaxWidth(),
                    label = R.string.product_stock_quantity,
                    value = "",
                    onValueChange = {}
                )
                //Add specification(name, description)
               Row(
                   modifier = Modifier.fillMaxWidth(),
                   verticalAlignment = Alignment.CenterVertically,
                   horizontalArrangement = Arrangement.SpaceBetween
               ) {
                   Text(
                       text = stringResource(id = R.string.specifications),
                       style = MaterialTheme.typography.titleLarge
                   )
                     IconButton(onClick = {
                          //Add specification
                     }) {
                          Icon(
                            modifier = Modifier.size(24.dp),
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_add),
                            contentDescription = stringResource(id = R.string.cd_add),
                            tint = MaterialTheme.colorScheme.onBackground
                          )
                     }

               }
                Column {
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        label = R.string.name,
                        value = "",
                        onValueChange = {}
                    )
                    InputField(
                        modifier = Modifier.fillMaxWidth(),
                        label = R.string.description,
                        value = "",
                        onValueChange = {}
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        text = R.string.add_photo,
                        onClick = {

                        },
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        textColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    ActionButton(
                        modifier = Modifier.weight(1f),
                        text = R.string.create_product,
                        onClick = {

                        },
                    )
                }
            }
        }
    }
}


@Composable
fun ManageProductScreenContent(
    modifier: Modifier = Modifier,
) {


}


@Composable
@Preview(showSystemUi = true)
fun ManageProductScreenPreview() {
    ManageProductScreenContent()
}
