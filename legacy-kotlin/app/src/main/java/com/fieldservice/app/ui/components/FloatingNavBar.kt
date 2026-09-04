package com.fieldservice.app.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

data class FloatingNavItem(val route: String, val label: String, val icon: ImageVector)

private val ItemSize = 56.dp
private val LabelZoneHeight = 28.dp
private val LabelZoneSpacing = 8.dp
private const val LabelVisibleDurationMs = 2000L

/**
 * Barra de navegação flutuante "glassmorphism": pílula translúcida com um glow que
 * segue o item selecionado e uma legenda animada acima do item ativo.
 */
@Composable
fun FloatingNavBar(
    items: List<FloatingNavItem>,
    selectedRoute: String?,
    onItemSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val selectedIndex = items.indexOfFirst { it.route == selectedRoute }.coerceAtLeast(0)
    val indicatorOffset by animateDpAsState(
        targetValue = ItemSize * selectedIndex,
        animationSpec = spring(dampingRatio = 0.65f, stiffness = 380f),
        label = "floatingNavIndicatorOffset"
    )

    val pillWidth = ItemSize * items.size

    var labelVisible by remember { mutableStateOf(false) }
    LaunchedEffect(selectedIndex) {
        labelVisible = true
        delay(LabelVisibleDurationMs)
        labelVisible = false
    }
    val labelAlpha by animateFloatAsState(
        targetValue = if (labelVisible) 1f else 0f,
        animationSpec = tween(durationMillis = 200),
        label = "floatingNavLabelAlpha"
    )

    Column(modifier = modifier, horizontalAlignment = Alignment.Start) {
        Box(
            modifier = Modifier
                .width(pillWidth)
                .height(LabelZoneHeight)
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .offset(x = indicatorOffset)
                    .width(ItemSize)
                    .wrapContentWidth(unbounded = true),
                contentAlignment = Alignment.Center
            ) {
                AnimatedContent(
                    targetState = items[selectedIndex].label,
                    modifier = Modifier.graphicsLayer { alpha = labelAlpha },
                    label = "floatingNavLabel"
                ) { label ->
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.85f)
                    ) {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            maxLines = 1,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(LabelZoneSpacing))

        Box(
            modifier = Modifier
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.75f))
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f), CircleShape)
        ) {
            Box(
                modifier = Modifier
                    .offset(x = indicatorOffset)
                    .size(ItemSize)
                    .blur(28.dp)
                    .background(
                        brush = Brush.radialGradient(
                            listOf(
                                MaterialTheme.colorScheme.primary.copy(alpha = 0.55f),
                                MaterialTheme.colorScheme.tertiary.copy(alpha = 0f)
                            )
                        ),
                        shape = CircleShape
                    )
            )

            Row {
                items.forEachIndexed { index, item ->
                    val selected = index == selectedIndex
                    val scale by animateFloatAsState(
                        targetValue = if (selected) 1.15f else 1f,
                        animationSpec = spring(dampingRatio = 0.5f, stiffness = 400f),
                        label = "floatingNavItemScale"
                    )

                    IconButton(
                        onClick = { onItemSelected(item.route) },
                        modifier = Modifier
                            .size(ItemSize)
                            .graphicsLayer {
                                scaleX = scale
                                scaleY = scale
                            }
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (selected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurfaceVariant
                            }
                        )
                    }
                }
            }
        }
    }
}
