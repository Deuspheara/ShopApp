package fr.deuspheara.eshopapp.ui.components.text

import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.auth.Password

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.text.PasswordField
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Password field
 *
 */
@Composable
fun PasswordField(
    modifier: Modifier = Modifier,
    @StringRes label: Int,
    password: Password,
    isError: Boolean,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    maxLines: Int = 1,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    isPasswordFocus: Boolean,
    onPasswordFocusChange: (Boolean) -> Unit,
    supportingText: @Composable (() -> Unit)? = null,
) {
    InputField(
        modifier = modifier,
        label = label,
        value = password.value,
        isError = isError,
        onValueChange = onValueChange,
        enabled = enabled,
        maxLines = maxLines,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            // Show eye icon for toggling password visibility
            IconButton(onClick = { onPasswordVisibilityChange(!isPasswordVisible) }) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = if (isPasswordVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_crossed),
                    contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                    tint = if (isPasswordFocus) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                )
            }
        },
        supportingText = {
            AnimatedVisibility(
                visible = isPasswordFocus,
                enter = expandVertically { -it },
                exit = shrinkVertically { it }
            ) {
                Text(
                    text = stringResource(id = R.string.password_requirement),
                    style = MaterialTheme.typography.bodySmall,
                    color = if (password.isStrong) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.error
                )
            }
        },
        onPasswordFocusChanged = onPasswordFocusChange
    )
}
