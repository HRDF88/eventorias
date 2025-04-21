package com.nedrysystems.eventorias.ui.component

import android.graphics.Bitmap
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.activity.result.launch
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.utils.image.BitmapUtils
import com.nedrysystems.eventorias.utils.image.BitmapUtils.uriToBitmap
import com.nedrysystems.eventorias.utils.image.SizeBitmapCONST

@Composable
fun PhotoPickerComposable(
    imageBitmap: Bitmap?,
    onImageBitmapChanged: (Bitmap?) -> Unit
) {
    val maxHeight = SizeBitmapCONST.maxHeight
    val maxWidth = SizeBitmapCONST.maxWidth
    val context = LocalContext.current

    var resizedBitmap by remember { mutableStateOf<Bitmap?>(null) }

    // Camera launcher
    val takePhoto = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        bitmap?.let {
            val resized = BitmapUtils.resize(it, maxWidth, maxHeight)
            resizedBitmap = resized
            onImageBitmapChanged(resized)
        }
    }

    // Gallery picker launcher
    val pickMedia = rememberLauncherForActivityResult(
        contract = PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                Log.d("PhotoPicker", "Selected URI: $uri")

                // Charger et redimensionner l'image
                val originalBitmap = uriToBitmap(context, uri)
                if (originalBitmap != null) {
                    val newBitmap = BitmapUtils.resize(originalBitmap, maxWidth, maxHeight)
                    resizedBitmap = newBitmap
                    onImageBitmapChanged(newBitmap)
                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(12.dp)
        ) {

            Button(
                onClick = { takePhoto.launch() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White
                ),
                modifier = Modifier
                    .defaultMinSize(minWidth = 45.dp, minHeight = 45.dp)

            ) {
                Icon(
                    painter = painterResource(R.drawable.photo_camera),
                    contentDescription = "",
                    tint = Color.Black,
                    modifier = Modifier.size(32.dp)
                )
            }

            Button(
                onClick = {
                    pickMedia.launch(PickVisualMediaRequest(PickVisualMedia.ImageOnly))
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                modifier = Modifier
                    .defaultMinSize(minWidth = 45.dp, minHeight = 45.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.attach_file),
                    contentDescription = "",
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
            }


        }
        Spacer(modifier = Modifier.height(8.dp))

        if (imageBitmap != null) {
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = "Selected Image",
                modifier = Modifier.size(200.dp)
            )
        } else if (resizedBitmap != null) {

            Image(
                bitmap = resizedBitmap!!.asImageBitmap(),
                contentDescription = "Resized Image",
                modifier = Modifier.size(200.dp)
            )
        } else {

            Text(
                stringResource(R.string.no_select_photo),
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(16.dp))


    }
}


@Composable
@Preview(showBackground = false)
fun PhotoPickerPreview() {
    PhotoPickerComposable(imageBitmap = null, onImageBitmapChanged = {})
}