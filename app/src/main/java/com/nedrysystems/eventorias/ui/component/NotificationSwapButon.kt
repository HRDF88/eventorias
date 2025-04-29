package com.nedrysystems.eventorias.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.nedrysystems.eventorias.R

@Composable
fun NotificationSwapButton(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    val notificationDescription = stringResource(R.string.toggle_notifications)
    val notificationState =
        if (isChecked) stringResource(R.string.notification_enabled) else stringResource(R.string.notification_disabled)
    Switch(
        checked = isChecked,
        onCheckedChange = onCheckedChange,
        colors = SwitchDefaults.colors(
            checkedThumbColor = Color.White,
            checkedTrackColor = Color.Red,
            uncheckedThumbColor = Color.White,
            uncheckedTrackColor = Color.Gray
        ),
        modifier = Modifier
            .padding(16.dp)
            .semantics { contentDescription = "$notificationDescription,  $notificationState" }
    )
}


