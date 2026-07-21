package com.shkurta.billy.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shkurta.billy.R
import com.shkurta.billy.ui.components.BillyTabSwitcher
import com.shkurta.billy.ui.theme.DarkGray
import com.shkurta.billy.ui.theme.Gray
import com.shkurta.billy.ui.theme.White
import java.util.Locale

enum class HomeTab {
    CALENDAR, LIST
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToNewEntry: (Int?) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    var selectedTab by remember { mutableStateOf(HomeTab.CALENDAR) }
    val entries by viewModel.entries.collectAsState()
    val totalCost by viewModel.totalMonthlyCost.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onNavigateToNewEntry(null) }) {
                Icon(Icons.Default.Add, contentDescription = "Add New Entry")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .statusBarsPadding()
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
                    modifier = Modifier.fillMaxWidth(0.7f),
                    optionToIcon = { tab ->
                        when (tab) {
                            HomeTab.CALENDAR -> R.drawable.ic_calendar
                            HomeTab.LIST -> R.drawable.ic_list
                        }
                    }
                )
            }

            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                when (selectedTab) {
                    HomeTab.CALENDAR -> {
                        CalendarView(
                            entries = entries,
                            totalCost = totalCost,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    HomeTab.LIST -> BillyListView(
                        entries = entries,
                        onEntryClick = { onNavigateToNewEntry(it.id) },
                        onDeleteClick = { viewModel.deleteEntry(it) }
                    )
                }
            }
        }
    }
}
