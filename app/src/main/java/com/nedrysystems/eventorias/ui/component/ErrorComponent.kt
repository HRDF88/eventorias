package com.nedrysystems.eventorias.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.theme.GraysEventoriasField

@Composable
fun ErrorComposable(
    onTryAgainClick: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
                .background(GraysEventoriasField)
                .padding(6.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.exclamation),
                contentDescription = "Icon",
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp),
                tint = Color.White
            )

        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = stringResource(R.string.error),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.padding(horizontal = 110.dp),
            horizontalArrangement = Arrangement.Center
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.error_display),
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = stringResource(R.string.try_later),
                    color = Color.White,
                    fontSize = 14.sp
                )

            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.padding(horizontal = 110.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {onTryAgainClick()},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ),
                shape = RectangleShape,
                modifier = Modifier.fillMaxWidth()

                ) {
                Text(
                    text = stringResource(R.string.try_again),
                    color = Color.White
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = false)
fun ErrorComposablePreview() {
    ErrorComposable({})
}
