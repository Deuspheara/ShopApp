package fr.deuspheara.eshopapp.ui.screens.product.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.products.Author
import fr.deuspheara.eshopapp.core.model.products.Availability
import fr.deuspheara.eshopapp.core.model.products.Brand
import fr.deuspheara.eshopapp.core.model.products.Category
import fr.deuspheara.eshopapp.core.model.products.Description
import fr.deuspheara.eshopapp.core.model.products.Identifier
import fr.deuspheara.eshopapp.core.model.products.ImageUrl
import fr.deuspheara.eshopapp.core.model.products.Name
import fr.deuspheara.eshopapp.core.model.products.Price
import fr.deuspheara.eshopapp.core.model.products.ProductFullModel
import fr.deuspheara.eshopapp.core.model.products.Rating
import fr.deuspheara.eshopapp.core.model.products.ReviewsCount
import fr.deuspheara.eshopapp.core.model.products.StockQuantity
import fr.deuspheara.eshopapp.data.network.model.shop.Specification
import fr.deuspheara.eshopapp.ui.components.loader.skeletonLoader
import fr.deuspheara.eshopapp.ui.theme.customGreen
import java.util.Currency

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.product.detail.ProductDetailScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Product detail screen
 *
 */


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductDetailScreen(
    viewModel: ProductDetailViewModel,
    onNavigateBack: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    val product by remember {
        derivedStateOf {
            (uiState as? ProductDetailUiState.Success)?.product
        }
    }

    val quantity by viewModel.cartQuantity.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = product?.name?.value ?: "",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            modifier = Modifier.height(24.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(
                            modifier = Modifier.height(24.dp),
                            painter = painterResource(id = R.drawable.ic_favorite),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            when (uiState) {
                is ProductDetailUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is ProductDetailUiState.Error -> {
                    Text(text = "Error")
                }

                is ProductDetailUiState.Success -> {
                    product?.let { ProductDetailContent(product = it, viewModel::addToCart, quantity.value) }
                }

            }
        }
    }
}

@Composable
fun ProductDetailContent(product: ProductFullModel, addToCart: () -> Unit = {}, quantity: Int? = null) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        item {
            // Product Image
            HorizontalPager(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.medium),
                state = rememberPagerState(pageCount = { product.images.size }, initialPage = 0),
            ) { page ->
                Image(
                    painter = rememberAsyncImagePainter(model = product.images[page].value),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .skeletonLoader()
                )

            }


        }
        item {
            // Product Price
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(top = 16.dp),
            ) {
                Text(
                    text = product.price.value.toString() + Currency.getInstance(product.price.currency).symbol,
                    style = MaterialTheme.typography.titleLarge,
                )
                
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = product.promotionPrice.value.toString() + Currency.getInstance(product.promotionPrice.currency).symbol,
                    style = MaterialTheme.typography.titleLarge + TextStyle(textDecoration = TextDecoration.LineThrough),
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                )
            }

        }

        item {
            Spacer(modifier = Modifier.height(8.dp))
        }

        item {
            // Product Description
            Text(
                text = product.description.value,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        items(product.specifications) { specification ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
                    .padding(8.dp),
            ) {
                Text(
                    text = specification.name,
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = specification.description,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        item {
            Spacer(modifier = Modifier.height(16.dp))
        }

        item {
            Button(
                onClick = addToCart,
                modifier = Modifier
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if(quantity == 0) MaterialTheme.colorScheme.customGreen else MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.add_to_cart),
                    color = Color.White
                )
            }
        }

        //TODO: Add reviews
    }
}

@Composable
@Preview
fun ProductDetailContentPreview() {
    val product = ProductFullModel(
        id = Identifier("1"),
        name = Name("Product Name"),
        author = Author("Product Author"),
        brand = Brand("Product Brand"),
        category = Category("Product Category"),
        availability = Availability(true),
        stockQuantity = StockQuantity(10),
        description = Description("Product Description"),
        images = listOf(ImageUrl("https://picsum.photos/200/300")),
        specifications = listOf(
            Specification(
                name = "Specification Name",
                description = "Specification Description"
            )
        ),
        price = Price(10.0, "USD"),
        promotionPrice = Price(5.0, "USD"),
        rating = Rating(4.5f),
        reviewsCount = ReviewsCount(10)
    )

    ProductDetailContent(product = product)
}