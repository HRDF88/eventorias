package com.nedrysystems.eventorias.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.theme.GrayEventoriasBackground

@Composable
fun BottomBar(navController: NavController) {
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(GrayEventoriasBackground) // Fond global pour la box
    ) {
        Row(
            modifier = Modifier
                .background(GrayEventoriasBackground)
                .fillMaxWidth()
        ) {
            NavigationBarItem(
                selected = currentDestination == "event",
                onClick = { navController.navigate("event") },
                icon = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.event),
                            contentDescription = "Événements",
                            tint = Color.White
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.events),
                        color = Color.White
                    )
                },

                modifier = Modifier
                    .background(GrayEventoriasBackground) // Fond pour chaque item
                    .weight(1f) // Prend la largeur égale
                    .padding(0.dp) // Aucun padding entre les items
            )

            NavigationBarItem(
                selected = currentDestination == "profile",
                onClick = { navController.navigate("profile") },
                icon = {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .padding(10.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.person),
                            contentDescription = "Profile",
                            tint = Color.White
                        )
                    }
                },
                label = {
                    Text(
                        text = stringResource(R.string.profile),
                        color = Color.White
                    )
                },
                modifier = Modifier
                    .background(GrayEventoriasBackground)
                    .weight(1f)
                    .padding(0.dp)
            )
        }
    }
}




@Preview
@Composable
fun PreviewBottomBar() {
    val navController = rememberNavController()
    BottomBar(navController = navController)
}
