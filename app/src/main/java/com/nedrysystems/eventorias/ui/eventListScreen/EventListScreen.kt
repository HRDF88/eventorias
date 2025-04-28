package com.nedrysystems.eventorias.ui.eventListScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.nedrysystems.eventorias.R
import com.nedrysystems.eventorias.ui.component.EventCard
import com.nedrysystems.eventorias.ui.theme.GrayEventoriasBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventListScreen(
    navController: NavController,
    viewModel: EventListViewModel
) {
    val eventState by viewModel.uiState.collectAsState()
    val errorMessage = eventState.error?.let { stringResource(id = it) } ?: ""
    val context = LocalContext.current
    val searchQuery by viewModel.searchQuery.collectAsState()
    val sortDescending by viewModel.sortDescending.collectAsState()
    var isSearchVisible by remember { mutableStateOf(false) }

    SideEffect {
        if (errorMessage.isNotEmpty()) {
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.loadAllEvents()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.event_list_tittle),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GrayEventoriasBackground
                ),
                actions = {
                    IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                        Icon(
                            painter = painterResource(R.drawable.search),
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = viewModel::toggleSortOrder) {
                        Icon(
                            painter = painterResource(R.drawable.swap_vert),
                            contentDescription = "Toggle Sort",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "add") },
                containerColor = Color.Red
            ) {
                Icon(
                    painter = painterResource(R.drawable.add),
                    contentDescription = "Add Event",
                    tint = Color.White
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .background(GrayEventoriasBackground)
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            if (isSearchVisible) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = viewModel::onSearchQueryChanged,
                    label = { Text(stringResource(R.string.search)) },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    colors = OutlinedTextFieldDefaults.colors(
                        Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                )
            }

            if (eventState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .background(GrayEventoriasBackground)
                        .padding(horizontal = 12.dp)
                        .fillMaxSize()
                ) {
                    items(eventState.events) { event ->
                        EventCard(
                            eventUi = event,
                            onClick = { navController.navigate("detail/${event.id}") })
                    }
                }
            }
        }
    }
}




