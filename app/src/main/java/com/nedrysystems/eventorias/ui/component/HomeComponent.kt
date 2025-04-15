package com.nedrysystems.eventorias.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.theme.GrayEventoriasBackground
import com.nedrysystems.eventorias.ui.theme.GraysEventoriasField

@Composable
fun BottomBar() {
    val navController = rememberNavController()
    val currentDestination = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination == "event",
                    onClick = { navController.navigate("event") },
                    icon = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(
                                    color = if (currentDestination == "event") GraysEventoriasField else GrayEventoriasBackground,
                                    shape = MaterialTheme.shapes.extraLarge
                                )
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
                    modifier = Modifier.background(GrayEventoriasBackground)
                )
                NavigationBarItem(
                    selected = currentDestination == "profile",
                    onClick = { navController.navigate("profile") },
                    icon = {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .background(
                                    color = if (currentDestination == "profile") GraysEventoriasField else GrayEventoriasBackground,
                                    shape = MaterialTheme.shapes.extraLarge
                                )
                                .padding(10.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.person),
                                contentDescription = "Événements",
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
                    modifier = Modifier.background(GrayEventoriasBackground)
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("home") { //HomeScreen() }
                composable("settings") { //SettingsScreen() }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBottomBar() {
    BottomBar()
}
