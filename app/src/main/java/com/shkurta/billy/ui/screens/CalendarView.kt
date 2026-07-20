package com.shkurta.billy.ui.screens

import androidx.compose.animation.animateColorAsState
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shkurta.billy.domain.model.Entry
import com.shkurta.billy.domain.model.EntryType
import com.shkurta.billy.ui.theme.Black
import com.shkurta.billy.ui.theme.DarkGray
import com.shkurta.billy.ui.theme.Gray
import com.shkurta.billy.ui.theme.LightGray
import com.shkurta.billy.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarView(
    entries: List<Entry>,
    modifier: Modifier = Modifier
) {
    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(initialPage = initialPage) { Int.MAX_VALUE }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Month Year Header
        val currentCalendar = remember(pagerState.currentPage) {
            Calendar.getInstance().apply {
                add(Calendar.MONTH, pagerState.currentPage - initialPage)
            }
        }
        val monthName = SimpleDateFormat("MMMM yyyy", Locale.getDefault()).format(currentCalendar.time)

        Text(
            text = monthName,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Weekday Headers (Fixed)
        val weekdays = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
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

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) { page ->
            val calendar = remember(page) {
                Calendar.getInstance().apply {
                    add(Calendar.MONTH, page - initialPage)
                    set(Calendar.DAY_OF_MONTH, 1)
                }
            }

            val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
            // Calendar.DAY_OF_WEEK: SUNDAY=1, MONDAY=2, ...
            // We want MONDAY=0, TUESDAY=1, ... SUNDAY=6
            val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val firstDayOffset = if (firstDayOfWeek == Calendar.SUNDAY) 6 else firstDayOfWeek - 2

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false // Let the pager handle horizontal swiping
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
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
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
