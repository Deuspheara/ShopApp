package fr.deuspheara.eshopapp.ui.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import fr.deuspheara.eshopapp.R

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.shop.section.ShopSection
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Shop section
 *
 */
@Composable
fun ShopSection(
    modifier: Modifier = Modifier,
    @StringRes title: Int,
    onClick: () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.onSurface,
    style: TextStyle = MaterialTheme.typography.titleLarge,
    @DrawableRes trailingIcon: Int = R.drawable.ic_arrow_right
) {
    Row(
        modifier = modifier
            .height(48.dp)
            .fillMaxWidth()
            .clickable(
                onClick = onClick,
                role = Role.Button
            )
            .padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(title),
            style = style,
            color = color,
        )

    }

}