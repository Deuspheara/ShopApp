package fr.deuspheara.eshopapp.ui.components.button

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
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
    onClick: () -> Unit,
    isLoading: Boolean
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        enabled = !isLoading
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(4.dp))
        } else {
            Text(stringResource(id = text))
        }
    }
}

@Composable
@Preview
fun ActionButtonPreview() {
    ActionButton(
        text =  R.string.sign_up_title,
        onClick = {},
        isLoading = false
    )
}