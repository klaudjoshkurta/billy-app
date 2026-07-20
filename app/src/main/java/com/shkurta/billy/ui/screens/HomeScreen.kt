package com.shkurta.billy.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shkurta.billy.ui.components.BillyTabSwitcher

enum class HomeTab {
    CALENDAR, LIST
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToNewEntry: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(HomeTab.CALENDAR) }
    val entries by viewModel.entries.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Billy") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNavigateToNewEntry) {
                Icon(Icons.Default.Add, contentDescription = "Add New Entry")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                BillyTabSwitcher(
                    options = HomeTab.entries,
                    selectedOption = selectedTab,
                    onOptionSelected = { selectedTab = it },
                    modifier = Modifier.fillMaxWidth(0.7f)
                )
            }

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                when (selectedTab) {
                    HomeTab.CALENDAR -> CalendarView(entries = entries)
                    HomeTab.LIST -> Text("List View (Coming Soon)")
                }
            }
        }
    }
}
