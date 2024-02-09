package fr.deuspheara.eshopapp.ui.screens.auth.signin

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.core.model.consume
import fr.deuspheara.eshopapp.ui.components.bar.top.ShopAppCenteredTopBar
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination
import fr.deuspheara.eshopapp.ui.theme.ShopAppTheme

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.auth.signin.SignInScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Sign in screen
 *
 */
@Composable
fun SignInScreen(
    viewModel: SignInViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
    onNavigateSignUp: () -> Unit
) {

    val uiState: SignInUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formInputState by viewModel.formInputState.collectAsStateWithLifecycle()

    val username by remember { derivedStateOf { formInputState.username } }
    val isUsernameError by remember { derivedStateOf { (uiState as? SignInUiState.FormInputError)?.isUsernameError == true } }

    val password by remember { derivedStateOf { formInputState.password } }
    val isPasswordError by remember { derivedStateOf { (uiState as? SignInUiState.FormInputError)?.isPasswordError == true } }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isPasswordFocus by rememberSaveable { mutableStateOf(false) }

    val isLoading by remember {
        derivedStateOf { (uiState as? SignInUiState.Loading)?.isLoading == true }
    }

    (uiState as? SignInUiState.SignInError)?.let {
        Toast.makeText(LocalContext.current, it.message, Toast.LENGTH_SHORT).show()
    }

    (uiState as? SignInUiState.SignInSuccess)?.consume()?.let {
        Toast.makeText(LocalContext.current, "Success", Toast.LENGTH_SHORT).show()
        onNavigateBack()
    }

    (uiState as? SignInUiState.FormInputError)?.let {
        Toast.makeText(LocalContext.current, "FormInput error", Toast.LENGTH_SHORT).show()
    }

    Scaffold(
        topBar = {
            ShopAppCenteredTopBar(
                destination = ShopAppDestination.SignInDestination,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        SignInContent(
            modifier = Modifier.padding(innerPadding),
            username = username,
            isUsernameError = isUsernameError,
            password = password,
            isPasswordError = isPasswordError,
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityChanged = { isPasswordVisible = it },
            onUsernameChanged = viewModel::onUsernameChanged,
            onPasswordChanged = viewModel::onPasswordChanged,
            isPasswordFocus = isPasswordFocus,
            onPasswordFocusChanged = { isPasswordFocus = it },
            onSignIn = viewModel::signIn,
            isLoading = isLoading,
            onNavigateToSignUp = onNavigateSignUp
        )

    }
}

@Composable
fun SignInContent(
    modifier: Modifier = Modifier,
    username: Username = Username(""),
    isUsernameError: Boolean = false,
    password: Password = Password(""),
    isPasswordError: Boolean = false,
    isPasswordVisible: Boolean = false,
    onPasswordVisibilityChanged: (Boolean) -> Unit = {},
    onUsernameChanged: (Username) -> Unit = {},
    onPasswordChanged: (Password) -> Unit = {},
    onSignIn: () -> Unit = {},
    isLoading: Boolean = false,
    isPasswordFocus: Boolean = false,
    onPasswordFocusChanged: (Boolean) -> Unit = {},
    onNavigateToSignUp: () -> Unit = {}
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        // Username input field
        OutlinedTextField(
            value = username.value,
            onValueChange = { onUsernameChanged(Username(it)) },
            label = { Text(stringResource(id = R.string.username)) },
            isError = isUsernameError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            enabled = !isLoading
        )

        // Password input field
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .onFocusChanged {
                    onPasswordFocusChanged(it.isFocused)
                },
            value = password.value,
            onValueChange = { onPasswordChanged(Password(it)) },
            label = { Text(stringResource(id = R.string.password)) },
            isError = isPasswordError,
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password
            ),
            trailingIcon = {
                IconButton(
                    onClick = {
                        onPasswordVisibilityChanged(!isPasswordVisible)
                    }
                ) {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(id = if (isPasswordVisible) R.drawable.ic_eye_open else R.drawable.ic_eye_crossed),
                        contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                        tint = if (isPasswordFocus) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.outline
                    )
                }
            },
            enabled = !isLoading,
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
            }
        )

        // Sign-in button
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = onSignIn,
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary
            )
        ) {
            Row {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.size(8.dp))
                Text(
                    stringResource(id = R.string.sign_in_title),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        TextButton(
            onClick = onNavigateToSignUp,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.dont_have_account),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignInContentPreview() {
    ShopAppTheme {
        SignInContent(
            username = Username("Hello"),
            password = Password("test129810*.J"),
            isPasswordFocus = true
        )
    }
}

