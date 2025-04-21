package com.nedrysystems.eventorias.ui.homeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nedrysystems.eventorias.ui.Screen
import com.nedrysystems.eventorias.ui.component.BottomBar
import com.nedrysystems.eventorias.ui.eventListScreen.EventListScreen
import com.nedrysystems.eventorias.ui.theme.GrayEventoriasBackground
import com.nedrysystems.eventorias.ui.userProfileScreen.UserProfileScreen

@Composable
fun HomeScreen(navController: NavController) {
    val internalNavController = rememberNavController()

    Scaffold(
        containerColor = GrayEventoriasBackground,
        bottomBar = { BottomBar(navController = internalNavController) }
    ) { innerPadding ->


        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(GrayEventoriasBackground)
                .padding(innerPadding)
        ) {
            NavHost(
                navController = internalNavController,
                startDestination = Screen.Event.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(Screen.Event.route) {
                    EventListScreen(
                        onFilterClick = { /* TODO */ },
                        onSearchClick = { /* TODO */ },
                        viewModel = hiltViewModel(),
                        navController = navController
                    )
                }
                composable(Screen.Profile.route) {
                    UserProfileScreen()
                }
            }
        }
    }
}

