package fr.deuspheara.eshopapp.ui.screens.auth.signup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.auth.Password
import fr.deuspheara.eshopapp.core.model.auth.Username
import fr.deuspheara.eshopapp.ui.components.bar.top.ShopAppCenteredTopBar
import fr.deuspheara.eshopapp.ui.components.button.ActionButton
import fr.deuspheara.eshopapp.ui.components.text.InputField
import fr.deuspheara.eshopapp.ui.components.text.PasswordField
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.auth.signup.SignUpScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Sign up screen
 *
 */
@Composable
fun SignUpScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formInputState by viewModel.formInputState.collectAsStateWithLifecycle()

    val username by remember { derivedStateOf { formInputState.username } }
    val isUsernameError by remember { derivedStateOf { (uiState as? SignUpUiState.FormInputError)?.isUsernameError == true } }

    val password by remember { derivedStateOf { formInputState.password } }
    val isPasswordError by remember { derivedStateOf { (uiState as? SignUpUiState.FormInputError)?.isPasswordError == true } }
    var isPasswordVisible by rememberSaveable { mutableStateOf(false) }
    var isPasswordFocus by rememberSaveable { mutableStateOf(false) }

    val isLoading by remember {
        derivedStateOf { (uiState as? SignUpUiState.Loading)?.isLoading == true }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            ShopAppCenteredTopBar(
                destination = ShopAppDestination.SignUpDestination,
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
        SignUpScreenContent(
            modifier = modifier.padding(innerPadding),
            onSignUp = viewModel::signUp,
            username = username,
            isUsernameError = isUsernameError,
            password = password,
            isPasswordError = isPasswordError,
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityChanged = { isPasswordVisible = it },
            onUsernameChanged = viewModel::onUsernameChanged,
            onPasswordChanged = viewModel::onPasswordChanged,
            isLoading = isLoading,
            isPasswordFocus = isPasswordFocus,
            onPasswordFocusChanged = { isPasswordFocus = it }
        )
    }
}

@Composable
fun SignUpScreenContent(
    modifier: Modifier = Modifier,
    username: Username = Username(""),
    isUsernameError: Boolean = false,
    password: Password = Password(""),
    isPasswordError: Boolean = false,
    isPasswordVisible: Boolean = false,
    onPasswordVisibilityChanged: (Boolean) -> Unit = {},
    onUsernameChanged: (Username) -> Unit = {},
    onPasswordChanged: (Password) -> Unit = {},
    onSignUp: () -> Unit = {},
    isLoading: Boolean = false,
    isPasswordFocus: Boolean = false,
    onPasswordFocusChanged: (Boolean) -> Unit = {}
) {
    Column(modifier = modifier.padding(horizontal = 16.dp)) {

        InputField(
            modifier = Modifier.fillMaxWidth(),
            label = R.string.username,
            value = username.value,
            onValueChange = { onUsernameChanged(Username(it)) },
            isError = isUsernameError,
            enabled = !isLoading,
        )

        InputField(
            modifier = Modifier.fillMaxWidth(),
            label = R.string.email,
            value = username.value,
            onValueChange = { onUsernameChanged(Username(it)) },
            isError = isUsernameError,
            enabled = !isLoading,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                autoCorrect = false
            ),
        )

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

        //additional information
        Text(
            text = stringResource(id = R.string.additional_information),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            InputField(
                modifier = Modifier.weight(1f),
                label = R.string.first_name,
                value = username.value,
                onValueChange = { onUsernameChanged(Username(it)) },
                isError = isUsernameError,
                enabled = !isLoading,
            )

            InputField(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 16.dp),
                label = R.string.last_name,
                value = username.value,
                onValueChange = { onUsernameChanged(Username(it)) },
                isError = isUsernameError,
                enabled = !isLoading,
            )
        }



        InputField(
            modifier = Modifier
                .fillMaxWidth(),
            label = R.string.address,
            value = username.value,
            onValueChange = { onUsernameChanged(Username(it)) },
            isError = isUsernameError,
            enabled = !isLoading,
        )


        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            InputField(
                modifier = Modifier.width(100.dp),
                label = R.string.zip_code,
                value = username.value,
                onValueChange = { onUsernameChanged(Username(it)) },
                isError = isUsernameError,
                enabled = !isLoading,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    autoCorrect = false
                ),
            )

            InputField(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(start = 16.dp),
                label = R.string.city,
                value = username.value,
                onValueChange = { onUsernameChanged(Username(it)) },
                isError = isUsernameError,
                enabled = !isLoading,
            )
        }

        PasswordField(
            modifier = Modifier.fillMaxWidth(),
            label = R.string.password,
            password = password,
            isError = isPasswordError,
            onValueChange = { onPasswordChanged(Password(it)) },
            isPasswordVisible = isPasswordVisible,
            onPasswordVisibilityChange = onPasswordVisibilityChanged,
            isPasswordFocus = isPasswordFocus,
            onPasswordFocusChange = onPasswordFocusChanged,
            enabled = !isLoading
        )

        ActionButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            text = R.string.sign_up_title,
            onClick = onSignUp,
            isLoading = isLoading
        )
    }
}

@Composable
@Preview(showSystemUi = true)
fun SignUpScreenPreview() {
    SignUpScreenContent()
}