package fr.deuspheara.eshopapp.ui.components.bar.search

import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import fr.deuspheara.eshopapp.R

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.bar.search.ShopAppSearchBar
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Search bar
 *
 */
@Composable
fun ShopAppSearchBar(
    modifier: Modifier = Modifier,
    @StringRes placeholder: Int,
    onSearch: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onClearQuery: () -> Unit,
    query: String,
    onQueryChange: (String) -> Unit,
    focused: Boolean = false,
    focusedValue: (Boolean) -> Unit,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    enabled: Boolean = true,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    val color by animateColorAsState(
        targetValue = if (focused) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surfaceVariant,
        label = "SearchBarColor"
    )

    SearchBar(
        query = query,
        onQueryChange = onQueryChange,
        onSearch = onSearch,
        active = focused,
        onActiveChange = focusedValue,
        modifier = modifier,
        placeholder = { Text(stringResource(placeholder)) },
        leadingIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    painter = if (focused) painterResource(id = R.drawable.ic_arrow_left) else painterResource(
                        R.drawable.ic_search
                    ),
                    contentDescription = stringResource(R.string.back),
                    tint = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.size(24.dp)
                )
            }
        },
        trailingIcon = {
            if (focused) {
                IconButton(onClick = onClearQuery) {
                    Icon(
                        painter = painterResource(R.drawable.ic_cross_small),
                        contentDescription = stringResource(R.string.clear),
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }

        },
        shape = RoundedCornerShape(8.dp),
        colors = SearchBarDefaults.colors(
            containerColor = color,
            dividerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f),
            inputFieldColors = SearchBarDefaults.inputFieldColors(
                focusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
                focusedTrailingIconColor = MaterialTheme.colorScheme.onSurface,
                focusedPlaceholderColor = MaterialTheme.colorScheme.onSurface,
                focusedTextColor = MaterialTheme.colorScheme.onSurface,
                unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTrailingIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLeadingIconColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledTrailingIconColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledPlaceholderColor = MaterialTheme.colorScheme.surfaceVariant,
                disabledTextColor = MaterialTheme.colorScheme.surfaceVariant,
            )

        ),
        interactionSource = interactionSource,
        content = content,
        enabled = enabled
    )
}