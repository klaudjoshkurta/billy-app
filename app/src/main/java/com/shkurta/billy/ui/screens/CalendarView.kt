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
import androidx.compose.material.icons.automirrored.filled.ReceiptLong
import androidx.compose.material.icons.filled.Subscriptions
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
import com.shkurta.billy.domain.model.PaymentFrequency
import com.shkurta.billy.ui.theme.Black
import com.shkurta.billy.ui.theme.DarkGray
import com.shkurta.billy.ui.theme.GeistMonoFontFamily
import com.shkurta.billy.ui.theme.Gray
import com.shkurta.billy.ui.theme.LightGray
import com.shkurta.billy.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun CalendarView(
    entries: List<Entry>,
    totalCost: Double,
    modifier: Modifier = Modifier
) {
    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(initialPage = initialPage) { Int.MAX_VALUE }
    
    val today = remember { Calendar.getInstance() }
    val todayDay = today.get(Calendar.DAY_OF_MONTH)
    val todayMonth = today.get(Calendar.MONTH)
    val todayYear = today.get(Calendar.YEAR)

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
            textAlign = TextAlign.Center
        )

        Text(
            text = "TOTAL MONTHLY COST: $${String.format(Locale.getDefault(), "%.2f", totalCost)}",
            fontSize = 11.sp,
            fontFamily = GeistMonoFontFamily,
            color = Gray,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
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
                        fontWeight = FontWeight.Bold,
                        fontFamily = GeistMonoFontFamily
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

            val pageMonth = calendar.get(Calendar.MONTH)
            val pageYear = calendar.get(Calendar.YEAR)

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
                    val entriesOnDay = entries.filter { entry ->
                        entry.dueDay == day && (
                            entry.frequency != PaymentFrequency.ONE_OFF || 
                            (entry.dueMonth == pageMonth && entry.dueYear == pageYear)
                        )
                    }
                    
                    val isToday = day == todayDay && pageMonth == todayMonth && pageYear == todayYear
                    
                    CalendarDayCell(
                        day = day,
                        entries = entriesOnDay,
                        isToday = isToday,
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
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        isToday -> White
        entries.isNotEmpty() -> DarkGray
        else -> LightGray
    }
    
    val contentColor = when {
        isToday -> Black
        entries.isNotEmpty() -> White
        else -> Black
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
    ) {
        // Day Number (Bottom Right)
        Text(
            text = day.toString(),
            color = contentColor,
            fontSize = 12.sp,
            fontFamily = GeistMonoFontFamily,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(4.dp)
        )

        // Entry Indicators (Center)
        if (entries.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Main Dot
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(if (isToday) Black else White)
                )
                
                if (entries.size > 1) {
                    Spacer(modifier = Modifier.size(2.dp))
                    Text(
                        text = "+${entries.size - 1}",
                        color = if (isToday) Black else White,
                        fontSize = 8.sp,
                        fontFamily = GeistMonoFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
