package fr.deuspheara.eshopapp.ui.components.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.deuspheara.eshopapp.R

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.card.CardButton
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Card button
 *
 */

@Composable
fun CardButton(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    @StringRes subtitle: Int,
    color: Color = MaterialTheme.colorScheme.surfaceVariant,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
                shape = MaterialTheme.shapes.medium
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
            .height(120.dp)
    ) {

        Icon(
            modifier = Modifier
                .size(40.dp)
                .background(color, MaterialTheme.shapes.small)
                .padding(8.dp)
            ,
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = stringResource(id = title),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = stringResource(id = title),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis,
            maxLines = 2
        )

        Text(
            text = stringResource(id = subtitle),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            overflow = TextOverflow.Ellipsis
        )


    }

}

@Composable
@Preview(showSystemUi = true)
fun CardButtonPreview() {
    CardButton(
        icon = R.drawable.ic_user,
        title = R.string.information_title,
        subtitle = R.string.order_subtitle
    )

}