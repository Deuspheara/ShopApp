package fr.deuspheara.eshopapp.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
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
 * fr.deuspheara.eshopapp.ui.components.card.SmallShopCard
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Small shop card
 *
 */


@Composable
fun SmallShopCard(
    modifier: Modifier = Modifier,
    name: Name,
    description: Description,
    urlImage: ImageUrl,
    onClick: () -> Unit = {},
    onFavoriteClick: () -> Unit = {},
    price: Price? = null,
    promotion: Price? = null,
) {

    Box(
        modifier = modifier
            .wrapContentHeight()
            .width(220.dp)
            .clip(MaterialTheme.shapes.large)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.TopEnd
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth(),


            ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large)
                    .height(140.dp),
                model = urlImage.value,
                contentDescription = name.value,
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = name.value,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    style = MaterialTheme.typography.bodyLarge,
                )
                Text(
                    text = description.value,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    style = MaterialTheme.typography.bodyMedium,
                )
                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    price?.let {
                        Text(
                            modifier = Modifier
                                .clip(MaterialTheme.shapes.large)
                                .background(MaterialTheme.colorScheme.surfaceVariant)
                                .padding(4.dp)
                                .padding(horizontal = 8.dp),
                            text = it.value.toString() + Currency.getInstance(it.currency).symbol,
                            color = MaterialTheme.colorScheme.tertiary,
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    promotion?.let {
                        Text(
                            text = it.value.toString() + Currency.getInstance(it.currency).symbol,
                            color = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.6f),
                            style = MaterialTheme.typography.bodyLarge
                                    + TextStyle(textDecoration = TextDecoration.LineThrough)
                        )
                    }
                }
            }
        }

        IconButton(
            modifier = Modifier
                .padding(4.dp),
            onClick = onFavoriteClick
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp),
                painter = painterResource(id = R.drawable.ic_favorite),
                contentDescription = stringResource(R.string.cd_favorite),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}