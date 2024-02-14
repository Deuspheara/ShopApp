package fr.deuspheara.eshopapp.ui.components.button

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.deuspheara.eshopapp.R

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.ActionButton
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Action button
 *
 */
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    @StringRes text: Int,
    @DrawableRes leadingIcon: Int? = null,
    onClick: () -> Unit,
    isLoading: Boolean = false,
    color: Color = MaterialTheme.colorScheme.primary,
    textColor: Color = MaterialTheme.colorScheme.onPrimary
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = !isLoading,
        colors = ButtonDefaults.buttonColors(
            containerColor = color
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            leadingIcon?.let<Int, @Composable (() -> Unit)?> {
                return@let {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        imageVector = ImageVector.vectorResource(id = leadingIcon),
                        contentDescription = null,
                        tint = textColor
                    )
                }
            }
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.padding(horizontal = 4.dp))
            } else {
                Text(
                    text = stringResource(id = text),
                    color = textColor
                )
            }
        }
    }
}

@Composable
@Preview
fun ActionButtonPreview() {
    Column {
        ActionButton(
            text = R.string.sign_up_title,
            onClick = {},
            isLoading = false
        )
        ActionButton(
            text = R.string.sign_up_title,
            onClick = {},
            isLoading = false,
            leadingIcon = R.drawable.ic_arrow_left
        )
    }
}