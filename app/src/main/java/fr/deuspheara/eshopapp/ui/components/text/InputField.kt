package fr.deuspheara.eshopapp.ui.components.text

import androidx.annotation.StringRes
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import fr.deuspheara.eshopapp.R

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.text.InputFields
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    value: String,
    isError: Boolean = false,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        autoCorrect = false
    ),
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    trailingIcon: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    onPasswordFocusChanged: (Boolean) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier
            .onFocusChanged {
                onPasswordFocusChanged(it.isFocused)
            },
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(stringResource(id = label)) },
        isError = isError,
        enabled = enabled,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        maxLines = maxLines,
        visualTransformation = visualTransformation,
        trailingIcon = trailingIcon,
        singleLine = maxLines == 1,
        supportingText = supportingText,
    )
}

@Preview(showBackground = true)
@Composable
fun InputFieldPreview() {
    InputField(
        label = R.string.username,
        value = "Deuspheara",
        isError = false,
        onValueChange = {}
    )
}