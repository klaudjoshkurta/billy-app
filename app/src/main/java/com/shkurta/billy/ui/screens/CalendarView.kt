package com.shkurta.billy.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shkurta.billy.domain.model.Entry
import com.shkurta.billy.domain.model.EntryType
import com.shkurta.billy.ui.theme.Black
import com.shkurta.billy.ui.theme.DarkGray
import com.shkurta.billy.ui.theme.Gray
import com.shkurta.billy.ui.theme.LightGray
import com.shkurta.billy.ui.theme.White
import java.util.Calendar

@Composable
fun CalendarView(
    entries: List<Entry>,
    modifier: Modifier = Modifier
) {
    val daysInMonth = 31 // Simplified for now, can be dynamic based on current month
    val weekdays = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
    
    // Calculate which day the 1st starts on (simplified for now: Monday = 0)
    val firstDayOffset = 0 

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Weekday Headers
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            weekdays.forEach { day ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(2.dp)
                        .height(24.dp)
                        .clip(CircleShape)
                        .background(DarkGray),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day,
                        color = White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Calendar Grid
        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Empty cells for offset
            items(firstDayOffset) {
                Box(modifier = Modifier.aspectRatio(1f))
            }

            items(daysInMonth) { index ->
                val day = index + 1
                val entriesOnDay = entries.filter { it.dueDay == day }
                
                CalendarDayCell(
                    day = day,
                    entries = entriesOnDay,
                    modifier = Modifier.padding(2.dp)
                )
            }
        }
    }
}

@Composable
fun CalendarDayCell(
    day: Int,
    entries: List<Entry>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(if (entries.isNotEmpty()) DarkGray else LightGray)
    ) {
        // Day Number (Bottom Right)
        Text(
            text = day.toString(),
            color = if (entries.isNotEmpty()) White else Black,
            fontSize = 12.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
        )

        // Entry Indicators (Center)
        if (entries.isNotEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                // For simplicity, just show one indicator or a dot
                // The image shows icons, we can use a dot or a generic icon for now
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(White),
                    contentAlignment = Alignment.Center
                ) {
                    val icon = if (entries.any { it.type == EntryType.SUBSCRIPTION }) {
                         // Some logic to show subscription icon
                         Icons.Default.Notifications
                    } else {
                        Icons.Default.Notifications
                    }
                    
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = Black,
                        modifier = Modifier.size(12.dp)
                    )
                }
                
                // Dot indicator (Top Right of cell)
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(4.dp)
                        .clip(CircleShape)
                        .background(White)
                        .align(Alignment.TopEnd)
                )
            }
        }
    }
}
