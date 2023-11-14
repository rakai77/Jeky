package com.example.jeky.presentation.screen.home

import android.Manifest
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import com.example.jeky.R
import com.example.jeky.domain.model.PlacesModel
import com.example.jeky.presentation.component.PointField
import com.example.jeky.presentation.screen.picklocation.PLACE_BUNDLE
import com.example.jeky.presentation.theme.Primary
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Polyline

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen(
    saveStateHandle: SavedStateHandle?,
    viewModel: HomeViewModel,
    onPickupClick: () -> Unit,
    onDestinationClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val placesResult by saveStateHandle?.getStateFlow(PLACE_BUNDLE, PlacesModel())!!.collectAsState()
    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    var pickup by remember {
        mutableStateOf(PlacesModel())
    }
    var destination by remember {
        mutableStateOf(PlacesModel())
    }

    val tracks = remember {
        mutableStateListOf<LatLng>()
    }

    LaunchedEffect(placesResult) {
        if (placesResult.locationName.isNotEmpty()) {
            if (placesResult.isPickupLocation)
                pickup = placesResult
            else destination = placesResult
        }
    }

    LaunchedEffect(pickup, destination) {
        if (pickup.locationName.isNotEmpty() && destination.locationName.isNotEmpty()) {
            viewModel.getPlaceRoutes(
                Pair(pickup.latitude, pickup.longitude),
                Pair(destination.latitude, destination.longitude)
            )
        }
    }

    LaunchedEffect(uiState) {
        (uiState as? HomeUiState.Success)?.data?.routes?.forEach { routeItem ->
            routeItem?.legs?.forEach { legsItem ->
                legsItem?.steps?.forEach { stepsItem ->
                    tracks.add(
                        LatLng(
                            stepsItem?.startLocation?.lat ?: 0.0,
                            stepsItem?.startLocation?.lng ?: 0.0
                        )
                    )
                    tracks.add(
                        LatLng(
                            stepsItem?.endLocation?.lat ?: 0.0,
                            stepsItem?.endLocation?.lng ?: 0.0
                        )
                    )
                }
            }
        }
    }



    if (locationPermissionState.allPermissionsGranted) {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                uiSettings = MapUiSettings(zoomControlsEnabled = false, compassEnabled = false)
            ) {
                if (tracks.isNotEmpty()) {
                    Polyline(points = tracks)
                }
            }
            PointField(modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .padding(horizontal = 24.dp),
                readOnly = true,
                pickupValue = pickup.locationName,
                destinationValue = destination.locationName,
                pickupPlaceholder = stringResource(id = R.string.pickup_location_txt),
                destinationPlaceholder = stringResource(R.string.destination_location_txt),
                onPickupFocused = onPickupClick,
                onDestinationFocused = onDestinationClick,
                onEditButtonClick = {}
            )
        }
    } else {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp), verticalArrangement = Arrangement.Center) {
            Text(
                text = stringResource(R.string.location_permission_message),
                style = MaterialTheme.typography.bodySmall.copy(
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 16.dp),
                shape = RoundedCornerShape(15.dp),
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Primary
                ),
                onClick = {
                    locationPermissionState.launchMultiplePermissionRequest()
                },
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                Text(
                    text = stringResource(R.string.allow_permission),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}