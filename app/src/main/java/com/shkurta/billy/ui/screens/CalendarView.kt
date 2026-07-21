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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Surface
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
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
    onEntryClick: (Entry) -> Unit,
    modifier: Modifier = Modifier
) {
    val initialPage = Int.MAX_VALUE / 2
    val pagerState = rememberPagerState(initialPage = initialPage) { Int.MAX_VALUE }
    
    val today = remember { Calendar.getInstance() }
    val todayDay = today.get(Calendar.DAY_OF_MONTH)
    val todayMonth = today.get(Calendar.MONTH)
    val todayYear = today.get(Calendar.YEAR)
    
    var selectedDay by remember { mutableIntStateOf(todayDay) }
    var selectedMonth by remember { mutableIntStateOf(todayMonth) }
    var selectedYear by remember { mutableIntStateOf(todayYear) }

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
            val firstDayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            val firstDayOffset = if (firstDayOfWeek == Calendar.SUNDAY) 6 else firstDayOfWeek - 2

            LazyVerticalGrid(
                columns = GridCells.Fixed(7),
                contentPadding = PaddingValues(0.dp),
                modifier = Modifier.fillMaxWidth(),
                userScrollEnabled = false
            ) {
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
                    val isSelected = day == selectedDay && pageMonth == selectedMonth && pageYear == selectedYear
                    
                    CalendarDayCell(
                        day = day,
                        entries = entriesOnDay,
                        isToday = isToday,
                        isSelected = isSelected,
                        onClick = {
                            selectedDay = day
                            selectedMonth = pageMonth
                            selectedYear = pageYear
                        },
                        modifier = Modifier.padding(2.dp)
                    )
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Agenda Section
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "AGENDA",
                fontSize = 12.sp,
                fontFamily = GeistMonoFontFamily,
                color = Gray,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            
            val agendaEntries = entries.filter { entry ->
                entry.dueDay == selectedDay && (
                    entry.frequency != PaymentFrequency.ONE_OFF || 
                    (entry.dueMonth == selectedMonth && entry.dueYear == selectedYear)
                )
            }
            
            if (agendaEntries.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No payments due on this day",
                        color = Gray,
                        fontSize = 14.sp
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(agendaEntries) { entry ->
                        AgendaItem(
                            entry = entry,
                            onClick = { onEntryClick(entry) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AgendaItem(
    entry: Entry,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        color = DarkGray,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(White),
                contentAlignment = Alignment.Center
            ) {
                val icon = if (entry.type == EntryType.SUBSCRIPTION) {
                    Icons.Default.Subscriptions
                } else {
                    Icons.AutoMirrored.Filled.ReceiptLong
                }
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = Black,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entry.name,
                    color = White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = entry.frequency.name.lowercase().replaceFirstChar { it.uppercase() }.replace("_", " "),
                    color = Gray,
                    fontSize = 12.sp
                )
            }
            
            Text(
                text = "$${String.format(Locale.getDefault(), "%.2f", entry.cost)}",
                color = White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = GeistMonoFontFamily
            )
        }
    }
}

@Composable
fun CalendarDayCell(
    day: Int,
    entries: List<Entry>,
    isToday: Boolean,
    isSelected: Boolean,
    onClick: () -> Unit,
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
    
    val borderModifier = if (isSelected && !isToday) {
        Modifier.border(2.dp, Gray, RoundedCornerShape(12.dp))
    } else Modifier

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(12.dp))
            .background(backgroundColor)
            .then(borderModifier)
            .clickable { onClick() }
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
