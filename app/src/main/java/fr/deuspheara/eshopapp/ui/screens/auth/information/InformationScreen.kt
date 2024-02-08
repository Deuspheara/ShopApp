package fr.deuspheara.eshopapp.ui.screens.auth.information

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.auth.Email
import fr.deuspheara.eshopapp.ui.components.bar.top.ShopAppCenteredTopBar
import fr.deuspheara.eshopapp.ui.components.button.ActionButton
import fr.deuspheara.eshopapp.ui.components.text.InputField
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.auth.information.InformationScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Information screen
 *
 */
@Composable
fun InformationScreen(
    viewModel: InformationViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val formInputState by viewModel.formInputState.collectAsStateWithLifecycle()

    val email by remember { derivedStateOf { formInputState.email } }
    val isEmailError by remember { derivedStateOf { (uiState as? InformationUiState.FormInputError)?.isEmailError == true } }

    val firstName by remember { derivedStateOf { formInputState.firstName } }
    val lastName by remember { derivedStateOf { formInputState.lastName } }
    val city by remember { derivedStateOf { formInputState.city } }
    val country by remember { derivedStateOf { formInputState.country } }
    val address by remember { derivedStateOf { formInputState.address } }
    val zipCode by remember { derivedStateOf { formInputState.zipCode } }


    val isLoading by remember {
        derivedStateOf { (uiState as? InformationUiState.Loading)?.isLoading == true }
    }

    (uiState as? InformationUiState.FormInputError)?.let { error ->
        Toast.makeText(LocalContext.current, error.toString(), Toast.LENGTH_SHORT).show()
    }
    Scaffold(
        topBar = {
            ShopAppCenteredTopBar(
                destination = ShopAppDestination.InformationDestination,
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
        InformationScreenContent(
            modifier = Modifier.padding(innerPadding),
            isLoading = isLoading,
            email = email, onEmailChanged = viewModel::onEmailChanged, isEmailError = isEmailError,
            firstName = firstName, onFirstNameChanged = viewModel::onFirstNameChanged,
            lastName = lastName, onLastNameChanged = viewModel::onLastNameChanged,
            address = address, onAddressChanged = viewModel::onAddressChanged,
            zipCode = zipCode, onZipCodeChanged = viewModel::onZipCodeChanged,
            city = city, onCityChanged = viewModel::onCityChanged,
            country = country, onCountryChanged = viewModel::onCountryChanged,
            submitForm = viewModel::submitForm
        )
    }
}

@Composable
fun InformationScreenContent(
    modifier: Modifier = Modifier,
    email: Email = Email(""), onEmailChanged: (Email) -> Unit = {}, isEmailError: Boolean = false,
    firstName: String = "", onFirstNameChanged: (String) -> Unit = {},
    lastName: String = "", onLastNameChanged: (String) -> Unit = {},
    address: String = "", onAddressChanged: (String) -> Unit = {},
    zipCode: String = "", onZipCodeChanged: (String) -> Unit = {},
    city: String = "", onCityChanged: (String) -> Unit = {},
    country: String = "", onCountryChanged: (String) -> Unit = {},
    isLoading: Boolean = false,
    submitForm: () -> Unit = {}
) {
  Column(
      modifier = modifier
          .fillMaxWidth()
          .padding(16.dp)
  ) {
      Text(
          text = stringResource(id = R.string.additional_information),
          style = MaterialTheme.typography.bodyLarge,
          color = MaterialTheme.colorScheme.onSurface
      )

      InputField(
          modifier = Modifier.fillMaxWidth(),
          label = R.string.email,
          value = email.value,
          onValueChange = { onEmailChanged(Email(it)) },
          isError = isEmailError,
          enabled = !isLoading,
          keyboardOptions = KeyboardOptions(
              keyboardType = KeyboardType.Email,
              autoCorrect = false
          ),
      )

      Row(
          modifier = Modifier.fillMaxWidth()
      ) {
          InputField(
              modifier = Modifier.weight(1f),
              label = R.string.first_name,
              value = firstName,
              onValueChange = { onFirstNameChanged(it) },
              enabled = !isLoading,
          )

          InputField(
              modifier = Modifier
                  .weight(1f)
                  .padding(start = 16.dp),
              label = R.string.last_name,
              value = lastName,
              onValueChange = { onLastNameChanged(it) },
              enabled = !isLoading,
          )
      }



      InputField(
          modifier = Modifier
              .fillMaxWidth(),
          label = R.string.address,
          value = address,
          onValueChange = { onAddressChanged(it) },
          enabled = !isLoading,
      )

      InputField(
          modifier = Modifier
              .fillMaxWidth(),
          label = R.string.country,
          value = country,
          onValueChange = { onCountryChanged(it) },
          enabled = !isLoading,
      )


      Row(
          modifier = Modifier.fillMaxWidth()
      ) {
          InputField(
              modifier = Modifier.width(100.dp),
              label = R.string.zip_code,
              value = zipCode,
              onValueChange = { onZipCodeChanged(it) },
              enabled = !isLoading,
              keyboardOptions = KeyboardOptions(
                  keyboardType = KeyboardType.Number,
                  autoCorrect = false
              )
            )

          InputField(
              modifier = Modifier
                  .weight(1.5f)
                  .padding(start = 16.dp),
              label = R.string.city,
              value = city,
              onValueChange = { onCityChanged(it) },
              enabled = !isLoading,
          )


      }

      ActionButton(
          onClick = submitForm,
          modifier = Modifier
              .padding(top = 16.dp)
              .fillMaxWidth(),
          isLoading = isLoading,
          text = R.string.save,
      )
  }
    
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun InformationScreenPreview() {
    InformationScreenContent(
        email = Email(""),
        onEmailChanged = {},
        isLoading = false,
        submitForm = {}
    )
}