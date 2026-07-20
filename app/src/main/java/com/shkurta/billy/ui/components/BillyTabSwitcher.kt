package com.shkurta.billy.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shkurta.billy.ui.theme.Black
import com.shkurta.billy.ui.theme.LightGray
import com.shkurta.billy.ui.theme.White

@Composable
fun <T> BillyTabSwitcher(
    options: List<T>,
    selectedOption: T,
    onOptionSelected: (T) -> Unit,
    modifier: Modifier = Modifier,
    optionToString: (T) -> String = { it.toString() }
) {
    val selectedIndex = options.indexOf(selectedOption)

    BoxWithConstraints(
        modifier = modifier
            .height(44.dp) // Slightly smaller height for a tighter feel
            .clip(CircleShape)
            .background(LightGray)
            .padding(2.dp) // Decreased padding from 4.dp to 2.dp
    ) {
        val indicatorWidth = maxWidth / options.size
        val animatedOffset by animateDpAsState(
            targetValue = indicatorWidth * selectedIndex,
            animationSpec = spring(stiffness = 500f), // Faster, smoother spring
            label = "indicatorOffset"
        )

        // Sliding Background Indicator
        Surface(
            modifier = Modifier
                .width(indicatorWidth)
                .fillMaxHeight()
                .offset(x = animatedOffset)
                .padding(2.dp), // Subtle padding within the container
            shape = CircleShape,
            color = White,
            shadowElevation = 2.dp
        ) {}

        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            options.forEach { option ->
                val isSelected = option == selectedOption
                
                val contentColor by animateColorAsState(
                    targetValue = if (isSelected) Black else Color.Gray,
                    label = "contentColor"
                )

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(CircleShape)
                        .clickable { onOptionSelected(option) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = optionToString(option).lowercase().replaceFirstChar { it.uppercase() },
                        color = contentColor,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }
        }
    }
}
