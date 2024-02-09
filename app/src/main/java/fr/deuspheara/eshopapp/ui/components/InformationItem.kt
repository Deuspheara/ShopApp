package fr.deuspheara.eshopapp.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.ui.components.loader.skeletonLoader

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.InformationItem
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Information item
 *
 */
@Composable
fun InformationItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    title: String?,
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Icon(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.shapes.small)
                .padding(8.dp)
                .size(24.dp)
            ,
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurface
        )
        title?.let {
            Text(
                text = it,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .skeletonLoader(isLoading = title.isEmpty()),
                style = MaterialTheme.typography.titleMedium,
            )
        }

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun InformationItemPreview() {
    Column {
        InformationItem(
            icon = R.drawable.ic_arrow_left,
            title = null
        )
        InformationItem(
            icon = R.drawable.ic_arrow_left,
            title = "deusheara@gmail.com"
        )
    }
}