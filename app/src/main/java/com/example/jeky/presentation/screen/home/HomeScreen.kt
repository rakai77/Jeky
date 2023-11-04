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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.jeky.R
import com.example.jeky.presentation.theme.Primary
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreen() {

    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )

    if (locationPermissionState.allPermissionsGranted) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = MapProperties(isMyLocationEnabled = true),
            uiSettings = MapUiSettings(zoomControlsEnabled = false, compassEnabled = false)
        ) {

        }
    } else {
        Column(modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp), verticalArrangement = Arrangement.Center) {
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