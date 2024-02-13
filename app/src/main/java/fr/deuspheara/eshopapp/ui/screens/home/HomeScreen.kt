package fr.deuspheara.eshopapp.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.products.Description
import fr.deuspheara.eshopapp.core.model.products.ImageUrl
import fr.deuspheara.eshopapp.core.model.products.Name
import fr.deuspheara.eshopapp.core.model.products.Price
import fr.deuspheara.eshopapp.ui.components.ShopSection
import fr.deuspheara.eshopapp.ui.components.bar.search.ShopAppSearch
import fr.deuspheara.eshopapp.ui.components.bar.top.ShopAppCenteredTopBar
import fr.deuspheara.eshopapp.ui.components.card.LargeShopCard
import fr.deuspheara.eshopapp.ui.components.card.ListShopCard
import fr.deuspheara.eshopapp.ui.components.card.SmallShopCard
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.home.HomeScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Home screen
 *
 */
@OptIn(ExperimentalFoundationApi::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    onNavigateToDetailedProduct: (String) -> Unit = {},
    onNavigateToSignIn: () -> Unit = {},
    onNavigateToCart: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {},
) {
    val context = LocalContext.current


    val topAppBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(topAppBarState)

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchText by viewModel.searchText.collectAsStateWithLifecycle()

    val isLoading by remember { derivedStateOf { (uiState as? HomeUiState.Loading)?.isLoading == true } }
    val isAuthenticated by viewModel.isAuthenticated.collectAsStateWithLifecycle()

    val categories by remember {
        derivedStateOf {
            (uiState as? HomeUiState.Categories)?.categories ?: emptyList()
        }
    }

    val filteredProducts = viewModel.filteredProducts.collectAsLazyPagingItems()

    val lazyPagingItems = viewModel.allProducts.collectAsLazyPagingItems()

    val recentLocalProducts = viewModel.recentLocalProducts.collectAsLazyPagingItems()

    val pagerState = rememberPagerState(pageCount = {
        lazyPagingItems.itemCount
    })

    var isSearchBarFocused by remember { mutableStateOf(false) }

    val focusManager = LocalFocusManager.current



    Scaffold(
        topBar = {
            Column {
                if (lazyPagingItems.loadState.refresh == LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .size(24.dp)
                    )
                }
                AnimatedVisibility(visible = !isSearchBarFocused) {
                    ShopAppCenteredTopBar(
                        destination = ShopAppDestination.HomeDestination,
                        isTitleVisible = false,
                        navigationIcon = {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    if (isAuthenticated) {
                                        onNavigateToProfile()
                                    } else {
                                        onNavigateToSignIn()
                                    }
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_user),
                                    contentDescription = stringResource(R.string.cd_menu),
                                    modifier = Modifier
                                        .size(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = onNavigateToCart) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_shopping_cart),
                                    contentDescription = stringResource(R.string.cd_shopping_cart),
                                    modifier = Modifier.height(24.dp),
                                    tint = MaterialTheme.colorScheme.onSurface,
                                )
                            }
                        },
                        scrollBehavior = scrollBehavior,
                    )
                }

            }


        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding),
            verticalArrangement = Arrangement.Top
        ) {
            ShopAppSearch(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = if (!isSearchBarFocused) 0.dp else 16.dp),
                placeholder = R.string.search,
                query = searchText.searchText,
                onQueryChange = { viewModel.onSearchTextChange(it) },
                focused = isSearchBarFocused,
                focusedValue = { isSearchBarFocused = it },
                onClearQuery = { viewModel.onSearchTextChange("") },
                onNavigateBack = {
                    viewModel.onSearchTextChange("")
                    focusManager.clearFocus()
                    isSearchBarFocused = false
                },
            ) {
                LazyColumn {
                    items(
                        count = filteredProducts.itemCount,
                        key = filteredProducts.itemKey { it.id },
                        contentType = filteredProducts.itemContentType { it },
                    ) { index ->
                        filteredProducts[index]?.let { product ->
                            ListShopCard(
                                name = Name(product.name ?: ""),
                                description = Description(product.description ?: ""),
                                urlImage = ImageUrl(product.images?.first() ?: ""),
                                onClick = { onNavigateToDetailedProduct(product.id) },
                                price = Price(product.price ?: 0.0, "USD"),
                                promotion = product.promotionPrice?.let { Price(it, "USD") },
                            )
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = !isSearchBarFocused,
                enter = slideInVertically { it },
            ) {
                Column(
                    modifier = Modifier
                        .nestedScroll(scrollBehavior.nestedScrollConnection)
                        .verticalScroll(rememberScrollState())
                        .padding(top = 16.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    LazyRow {
                        items(categories) { category ->
                            TextButton(
                                modifier = Modifier
                                    .padding(start = 16.dp),
                                onClick = {},
                                shape = RoundedCornerShape(32.dp),
                                colors = ButtonDefaults.buttonColors(
                                    contentColor = MaterialTheme.colorScheme.onSurface,
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                                    disabledContentColor = MaterialTheme.colorScheme.onSurface,
                                ),
                            ) {
                                Text(text = category.value)
                            }
                        }
                    }
                    recentLocalProducts.itemCount.takeIf { it > 0 }?.run {
                       ShopSection(
                           title = R.string.continue_shopping
                       )

                    }
                    LazyRow {

                        items(
                            count = recentLocalProducts.itemCount,
                            key = recentLocalProducts.itemKey { it.id },
                            contentType = recentLocalProducts.itemContentType { it },
                        ) { index ->
                            recentLocalProducts[index]?.let { product ->
                                SmallShopCard(
                                    modifier = Modifier
                                        .padding(start = 16.dp),
                                    name = Name(product.name ?: ""),
                                    description = Description(product.description ?: ""),
                                    urlImage = ImageUrl(product.images?.first() ?: ""),
                                    onClick = {
                                        onNavigateToDetailedProduct(product.id)
                                    },
                                    onFavoriteClick = {},
                                    price = Price(product.price ?: 0.0, "USD"),
                                    promotion = product.promotionPrice?.let { Price(it, "USD") }
                                )
                            }
                        }

                    }

                    ShopSection(
                        title = R.string.category_computer,
                        onClick = {},
                    )

                    HorizontalPager(
                        state = pagerState,
                        key = lazyPagingItems.itemKey { it.id },
                    ) { index ->
                        lazyPagingItems[index]?.let { product ->
                            LargeShopCard(
                                imageUrl = ImageUrl(product.images?.first() ?: ""),
                                name = Name(product.name ?: ""),
                                description = Description(product.description ?: ""),
                                price = Price(product.price ?: 0.0, "USD"),
                                promotion = product.promotionPrice?.let { Price(it, "USD") },
                                onClick = {
                                    onNavigateToDetailedProduct(product.id)
                                },
                            )
                        }
                    }
                }
            }
        }
    }
}
