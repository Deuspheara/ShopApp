package fr.deuspheara.eshopapp.ui.components.shop.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.products.Description
import fr.deuspheara.eshopapp.core.model.products.ImageUrl
import fr.deuspheara.eshopapp.core.model.products.Name
import fr.deuspheara.eshopapp.core.model.products.Price
import java.util.Currency

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.shop.card.ListShopCard
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * List shop card
 *
 */
@Composable
fun ListShopCard(
    modifier: Modifier = Modifier,
    name: Name,
    description: Description,
    urlImage: ImageUrl,
    onClick: () -> Unit = {},
    price: Price? = null,
    promotion: Price? = null,
) {
    Column {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .height(100.dp)
                .clickable(onClick = onClick),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                modifier = Modifier
                    .size(100.dp)
                    .padding(16.dp)
                    .clip(MaterialTheme.shapes.medium),
                model = urlImage.value,
                contentDescription = name.value,
                contentScale = ContentScale.Crop,
                error = painterResource(id = R.drawable.ic_cross_small),
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = name.value,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = description.value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (promotion != null) {
                        Text(
                            text = promotion.value.toString() + Currency.getInstance(promotion.currency).symbol,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                    }
                    if (price != null) {
                        Text(
                            text = price.value.toString() + Currency.getInstance(price.currency).symbol,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))

            Icon(
                modifier = Modifier
                    .padding(24.dp)
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_arrow_up),
                contentDescription = stringResource(id = R.string.cd_arrow_up),
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
        )
    }

}

@Composable
@Preview(showBackground = true)
fun ListShopCardPreview() {
    ListShopCard(
        name = Name("Product 1"),
        description = Description("Description 1"),
        urlImage = ImageUrl("https://picsum.photos/200/300"),
        price = Price(10.0, "USD"),
        promotion = Price(5.0, "USD")
    )
}