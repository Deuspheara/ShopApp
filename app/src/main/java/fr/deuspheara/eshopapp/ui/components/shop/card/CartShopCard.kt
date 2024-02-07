package fr.deuspheara.eshopapp.ui.components.shop.card

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.products.Description
import fr.deuspheara.eshopapp.core.model.products.ImageUrl
import fr.deuspheara.eshopapp.core.model.products.Name
import fr.deuspheara.eshopapp.core.model.products.Price
import fr.deuspheara.eshopapp.core.model.products.Quantity
import java.util.Currency

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.shop.card.CartShopCard
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Cart shop card
 *
 */
@Composable
fun CartShopCard(
    modifier: Modifier = Modifier,
    imageUrl: ImageUrl? = null,
    name: Name,
    description: Description,
    price: Price,
    promotionPrice: Price? = null,
    quantity: Quantity,
    onClickMinus: () -> Unit = {},
    onClickPlus: () -> Unit = {},
    onClick: () -> Unit = {}
    ) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(150.dp)
            .clickable { onClick() }
    ) {
        imageUrl?.let {
            AsyncImage(
                modifier = Modifier
                    .width(150.dp)
                    .padding(8.dp)
                    .clip(MaterialTheme.shapes.medium),
                model = it.value,
                contentDescription = name.value,
                contentScale = ContentScale.Crop,
            )
        }
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = price.value.toString() + Currency.getInstance(price.currency).symbol,
                    style = MaterialTheme.typography.bodyLarge
                )
                promotionPrice?.let {
                    Text(
                        text = promotionPrice.value.toString() + Currency.getInstance(promotionPrice.currency).symbol,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        textDecoration = TextDecoration.LineThrough
                    )
                }

            }
            Text(
                text = description.value,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.weight(1f))
            QuantitySelector(quantity = quantity, onClickMinus = onClickMinus, onClickPlus = onClickPlus)
            

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun CartShopCardPreview() {
    CartShopCard(
        imageUrl = ImageUrl("https://picsum.photos/200/300"),
        name = Name("Product name"),
        description = Description("Product description"),
        price = Price(10.0, "USD"),
        promotionPrice = Price(5.0, "USD"),
        quantity = Quantity(1),
        onClick = {},
    )
}

@Composable
fun QuantitySelector(
    quantity: Quantity,
    onClickMinus: () -> Unit = {},
    onClickPlus: () -> Unit = {},
) {
    Row (
        verticalAlignment = Alignment.CenterVertically
    ){
        IconButton(
            onClick = onClickMinus,
            enabled = quantity.value > 0,
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.ic_minus),
                contentDescription = stringResource(id = R.string.cd_minus),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            modifier = Modifier
                .padding(horizontal = 8.dp),
            text = quantity.value.toString(),
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onBackground
        )

        IconButton(
            onClick = onClickPlus
        ) {
            Icon(
                modifier = Modifier
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.onBackground, CircleShape)
                    .padding(8.dp),
                painter = painterResource(id = R.drawable.ic_plus),
                contentDescription = stringResource(id = R.string.cd_plus),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}