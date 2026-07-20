package com.shkurta.billy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shkurta.billy.domain.model.Entry
import com.shkurta.billy.ui.theme.Black
import com.shkurta.billy.ui.theme.Gray
import com.shkurta.billy.ui.theme.LightGray
import java.util.Locale

@Composable
fun BillyListView(
    entries: List<Entry>,
    onEntryClick: (Entry) -> Unit,
    onDeleteClick: (Entry) -> Unit,
    modifier: Modifier = Modifier
) {
    if (entries.isEmpty()) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "No entries yet", color = Gray)
        }
    } else {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(entries) { entry ->
                BillyListItem(
                    entry = entry,
                    onClick = { onEntryClick(entry) },
                    onDelete = { onDeleteClick(entry) }
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 0.5.dp,
                    color = LightGray
                )
            }
        }
    }
}

@Composable
fun BillyListItem(
    entry: Entry,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Black
            )
            Text(
                text = "${entry.type.name.lowercase().replaceFirstChar { it.uppercase() }} • ${entry.frequency.name.lowercase().replaceFirstChar { it.uppercase() }.replace("_", " ")}",
                fontSize = 12.sp,
                color = Gray
            )
        }
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "$${String.format(Locale.getDefault(), "%.2f", entry.cost)}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = Black
            )
            
            IconButton(onClick = onDelete) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Gray,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}
