package fr.deuspheara.eshopapp.ui.components.bar.search

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
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
    onClearQuery: () -> Unit,
    onNavigateBack: () -> Unit,
    focused : Boolean = false,
    focusedValue: (Boolean) -> Unit = {},
    content: @Composable () -> Unit = {}
) {

    Column(
        modifier = modifier
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(32.dp)
                ).onFocusEvent {
                    focusedValue(it.isFocused)
                },
            colors = TextFieldDefaults.colors(
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
            ),
            shape = CircleShape,
            value = query,
            onValueChange = onQueryChange,
            maxLines = maxLines,
            textStyle = MaterialTheme.typography.bodyLarge + TextStyle(
                color = if (focused) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
            ),
            trailingIcon = trailingIcon?.let<Int, @Composable (() -> Unit)?> {
                return@let {
                    if (focused) {
                        IconButton(
                            onClick = onClearQuery
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_cross_small),
                                contentDescription = stringResource(R.string.clear),
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                }
            },
            leadingIcon = leadingIcon?.let<Int, @Composable (() -> Unit)?> {
                return@let {
                    IconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = onNavigateBack
                    ) {
                        Icon(
                            painter = if (focused) painterResource(id = R.drawable.ic_arrow_left) else painterResource(
                                R.drawable.ic_search
                            ),
                            contentDescription = stringResource(R.string.back),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.size(24.dp)
                        )
                    }
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
        AnimatedVisibility(
            visible = focused,
            enter = slideInVertically(
                animationSpec = spring(stiffness = 400f)
            ){ it },
        ) {
            content()
        }

    }
}