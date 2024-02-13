package fr.deuspheara.eshopapp.ui.screens.cart

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.products.Description
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.core.model.products.ImageUrl
import fr.deuspheara.eshopapp.core.model.products.Name
import fr.deuspheara.eshopapp.core.model.products.Price
import fr.deuspheara.eshopapp.core.model.products.ProductCartModel
import fr.deuspheara.eshopapp.core.model.products.Quantity
import fr.deuspheara.eshopapp.ui.components.bar.top.ShopAppCenteredTopBar
import fr.deuspheara.eshopapp.ui.components.card.CartShopCard
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
import java.text.DecimalFormat
import java.util.Currency

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.cart.CartScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Cart screen
 *
 */

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val products = (uiState as? CartUiState.Success)?.products ?: emptyList()

    val isLoading by remember { derivedStateOf { (uiState as? CartUiState.Loading)?.isLoading == true } }

    Scaffold(
        topBar = {
            ShopAppCenteredTopBar(
                destination = ShopAppDestination.CartDestination,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = stringResource(R.string.cd_arrow_up),
                            modifier = Modifier
                                .size(24.dp),
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        CartScreenContent(
            modifier = modifier
                .padding(innerPadding),
            products = products,
            decrementProductQuantity = viewModel::decrementCartItemQuantity,
            incrementProductQuantity = viewModel::incrementCartItemQuantity
        )
    }

}


@Composable
fun CartScreenContent(
    modifier: Modifier = Modifier,
    products: List<ProductCartModel> = emptyList(),
    incrementProductQuantity: (productId: Identifier) -> Unit = {},
    decrementProductQuantity: (productId: Identifier) -> Unit = {},
    onNavigateToDetailedProduct: (productId: Identifier) -> Unit = {}
) {
    val total = products.sumOf { it.price.value * it.quantity.value }
    val decimalFormat = DecimalFormat("0.00")

    Log.d("CartScreenContent", "Total: $total")

    LazyColumn(
        modifier = modifier
    ) {
        item {
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Total ${Currency.getInstance("USD").symbol}${decimalFormat.format(total)}",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                onClick = { /*TODO*/ },
            ) {
                Text(
                    text = "Checkout",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

           HorizontalDivider(
                modifier = Modifier.padding(16.dp)
           )
        }
        items(
            products,
            key = { product -> product.id.value }
        ) {
            CartShopCard(
                imageUrl = it.imageUrl,
                name = it.name,
                description = it.description,
                price = it.price,
                promotionPrice = it.promotionPrice,
                quantity = it.quantity,
                onClick = { onNavigateToDetailedProduct(it.id) },
                onClickMinus = { decrementProductQuantity(it.id) },
                onClickPlus = { incrementProductQuantity(it.id) }
            )
        }
    }

}

@Composable
@Preview(showSystemUi = true)
fun CartScreenPreview() {
    CartScreenContent(
        products = listOf(
            ProductCartModel(
                id = Identifier("1"),
                name = Name("Product name"),
                description = Description("Product description"),
                price = Price(10.0, "USD"),
                promotionPrice = Price(5.0, "USD"),
                quantity = Quantity(1),
                imageUrl = ImageUrl("https://picsum.photos/200/300")
            )
        )
    )
}