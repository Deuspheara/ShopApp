package fr.deuspheara.eshopapp.ui.components.bar.search

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import fr.deuspheara.eshopapp.R

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.bar.search.ShopAppSearch
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Search
 *
 */
@Composable
fun ShopAppSearch(
    modifier: Modifier = Modifier,
    query: String = "",
    onQueryChange: (String) -> Unit = {},
    maxLines: Int = 1,
    @StringRes placeholder: Int = R.string.search,
    @DrawableRes leadingIcon: Int? = R.drawable.ic_search,
    @DrawableRes trailingIcon: Int? = null,
    onTrailingIconClick: () -> Unit = {},
    focusedValue: (Boolean) -> Unit = {},
) {
    val focused = remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(8.dp)
            )
            .onFocusChanged {
                focused.value = it.isFocused
                focusedValue(it.isFocused)
            },
        colors = TextFieldDefaults.colors(
            disabledTextColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(8.dp),
        value = query,
        onValueChange = onQueryChange,
        maxLines = maxLines,
        textStyle = MaterialTheme.typography.bodyLarge + TextStyle(
            color = if (focused.value) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
        ),
        trailingIcon = trailingIcon?.let<Int, @Composable (() -> Unit)?> {
            return@let {
                IconButton(
                    onClick = onTrailingIconClick
                ) {
                    Icon(
                        painter = painterResource(id = trailingIcon),
                        contentDescription = stringResource(id = placeholder),
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.outline
                    )
                }

            }
        },
        leadingIcon = leadingIcon?.let<Int, @Composable (() -> Unit)?> {
            return@let {
                Icon(
                    painter = painterResource(id = leadingIcon),
                    contentDescription = stringResource(id = placeholder),
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onSurface
                )
            }
        },
        placeholder = {
            Text(
                text = stringResource(id = placeholder),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
    )
}