package com.example.jeky.presentation.screen.picklocation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jeky.R
import com.example.jeky.core.domain.model.DisplayName
import com.example.jeky.core.domain.model.Location
import com.example.jeky.core.domain.model.PlacesItem
import com.example.jeky.presentation.component.PointField
import com.example.jeky.presentation.theme.Border
import com.example.jeky.presentation.theme.LightGray
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

@Composable
fun PickLocationBottomSheet(
    viewModel: PickLocationViewModel,
    isToGetPickupLocation: Boolean,
    onClose: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()

    var pickup by remember {
        mutableStateOf("")
    }
    var destination by remember {
        mutableStateOf("")
    }

    LaunchedEffect(pickup) {
        snapshotFlow {
            pickup
        }
            .distinctUntilChanged()
            .debounce(1000)
            .filter { it.isNotEmpty() }
            .collect {
                viewModel.getPlaces(it)
            }

    }

    LaunchedEffect(destination) {
        snapshotFlow {
            destination
        }
            .distinctUntilChanged()
            .debounce(1000)
            .filter { it.isNotEmpty() }
            .collect {
                viewModel.getPlaces(it)
            }

    }

    val title = if (isToGetPickupLocation) {
        stringResource(id = R.string.pickup_title)
    } else stringResource(id = R.string.destination_title)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(top = 16.dp)
        ) {
            IconButton(
                modifier = Modifier.size(24.dp),
                onClick = onClose
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Close Bottom Sheet"
                )
            }
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = title,
                style = MaterialTheme.typography.labelLarge.copy(
                    fontSize = 18.sp
                )
            )
        }
        PointField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 18.dp)
                .padding(horizontal = 24.dp),
            pickupValue = pickup,
            destinationValue = destination,
            pickupPlaceholder = stringResource(id = R.string.pickup_location_txt),
            destinationPlaceholder = stringResource(R.string.destination_location_txt),
            onPickupFocused = {},
            onDestinationFocused = {},
            elevation = 0.dp,
            backgroundColor = LightGray,
            borderColor = Border,
            editButtonVisible = false,
            onPickupValueChange = {
                pickup = it
            },
            onDestinationValueChange = {
                destination = it
            },
            onEditButtonClick = {

            }
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            thickness = 1.dp,
            color = Border
        )

        when (uiState) {
            is PickLocationUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items((uiState as? PickLocationUiState.Success)?.data?.places.orEmpty()) {
                        PlaceItem(
                            place = it,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            onClick = {

                            }
                        )
                    }
                }
            }
            else -> Unit
        }
    }
}


@Composable
fun PlaceItem(
    place: PlacesItem,
    modifier: Modifier,
    onClick: (PlacesItem) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_place),
            tint = Color.Unspecified,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(text = place.displayName.text, style = MaterialTheme.typography.labelMedium)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = place.formattedAddress, style = MaterialTheme.typography.bodySmall)
        }
    }
}