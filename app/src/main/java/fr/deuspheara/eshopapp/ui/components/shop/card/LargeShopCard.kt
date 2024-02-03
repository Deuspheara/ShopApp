package fr.deuspheara.eshopapp.ui.components.shop.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.products.Description
import fr.deuspheara.eshopapp.core.model.products.ImageUrl
import fr.deuspheara.eshopapp.core.model.products.Name
import fr.deuspheara.eshopapp.core.model.products.Price

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.shop.card.LargeShopCard
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Large shop card
 *
 */
@Composable
fun LargeShopCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    imageUrl: ImageUrl,
    name: Name,
    description: Description,
    price: Price? = null,
    promotion: Price? = null,
    onFavoriteClick: () -> Unit = {},
    addToCart: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = MaterialTheme.shapes.medium
            )
            .clip(MaterialTheme.shapes.medium)
            .clickable(onClick = onClick)
            .fillMaxWidth()
            .height(300.dp)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = name.value,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 2,
            )
            Text(
                text = description.value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(24.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = price?.value.toString() + price?.currency,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = promotion?.value.toString() + promotion?.currency,
                    style = MaterialTheme.typography.bodyLarge + TextStyle(textDecoration = TextDecoration.LineThrough),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                )
            }
            Spacer(modifier = Modifier.weight(1f))

            Row(
                modifier = Modifier
                    .offset(x = (-8).dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onClick = onFavoriteClick
                ) {
                    Icon(
                        modifier = Modifier.height(24.dp),
                        painter = painterResource(id = R.drawable.ic_favorite),
                        contentDescription = stringResource(id = R.string.cd_favorite),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }

                IconButton(
                    modifier = Modifier
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    onClick = addToCart
                ) {
                    Icon(
                        modifier = Modifier.height(28.dp),
                        painter = painterResource(id = R.drawable.ic_shopping_cart),
                        contentDescription = stringResource(id = R.string.cd_shopping_cart),
                        tint = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }

        AsyncImage(
            modifier = Modifier
                .clip(MaterialTheme.shapes.medium)
                .fillMaxHeight()
                .weight(1.5f),
            model = imageUrl.value,
            contentDescription = null,
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun LargeShopCardPreview() {
    LargeShopCard(
        imageUrl = ImageUrl("https://picsum.photos/200/300"),
        name = Name("Nike Air Max 270"),
        description = Description("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."),
        price = Price(100.0, "USD"),
        promotion = Price(150.0, "USD"),
    )
}
