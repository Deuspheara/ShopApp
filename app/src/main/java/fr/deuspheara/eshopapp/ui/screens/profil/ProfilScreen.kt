package fr.deuspheara.eshopapp.ui.screens.profil

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fr.deuspheara.eshopapp.R
import fr.deuspheara.eshopapp.core.model.auth.UserFullModel
import fr.deuspheara.eshopapp.ui.components.InformationItem
import fr.deuspheara.eshopapp.ui.components.bar.top.ShopAppCenteredTopBar
import fr.deuspheara.eshopapp.ui.navigation.ShopAppDestination

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.screens.profil.ProfilScreen
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 *
 *
 */
@Composable
fun ProfilScreen(
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            ShopAppCenteredTopBar(
                destination = ShopAppDestination.ProfilDestination,
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_arrow_left),
                            contentDescription = stringResource(id = R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        //logout
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(id = R.drawable.ic_logout),
                            contentDescription = stringResource(id = R.string.logout)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ProfileScreenContent(
            modifier = modifier
                .padding(innerPadding),
            userFullModel = (uiState as? ProfileUiState.Success)?.user
        )
    }
}

@Composable
fun ProfileScreenContent(
    modifier: Modifier = Modifier,
    userFullModel: UserFullModel?
) {
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.Start
    ) {
        userFullModel?.apply {
            username?.let {
                InformationItem(
                    icon = R.drawable.ic_user,
                    title = username,
                )
            }
            if (firstName != null || lastName != null) {
                InformationItem(
                    icon = R.drawable.ic_name,
                    title = firstName.orEmpty() + " " + lastName.orEmpty(),
                )
            }
            email?.let {
                InformationItem(
                    icon = R.drawable.ic_email,
                    title = email,
                )
            }

            Text(
                text = stringResource(id = R.string.information_title),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(16.dp)
            )

            if (zipCode != null || address != null) {
                InformationItem(
                    icon = R.drawable.ic_location,
                    title = zipCode.orEmpty() + " " + address.orEmpty(),
                )
            }

            city?.let { city ->
                InformationItem(
                    icon = R.drawable.ic_city,
                    title = city,
                )
            }

            country?.let { country ->
                InformationItem(
                    icon = R.drawable.ic_country,
                    title = country,
                )
            }


        }

    }
}

@Composable
@Preview(showBackground = true)
fun ProfilScreenPreview() {
    ProfilScreen(
        onNavigateBack = {}
    )
}