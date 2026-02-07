package com.yoyostudios.clashcompanion.ui.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yoyostudios.clashcompanion.ui.theme.CrColors
import com.yoyostudios.clashcompanion.ui.theme.CrTypography

/**
 * 3D-style button matching CR's "Battle" button.
 * V2: thicker bottom edge, highlight line, chunkier padding, more saturated.
 */
@Composable
fun CrButtonGold(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: Painter? = null
) {
    CrButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        icon = icon,
        gradientTop = CrColors.GoldLight,
        gradientBottom = CrColors.GoldDark,
        borderBottom = CrColors.GoldBorder,
        disabledGradientTop = CrColors.TealMid.copy(alpha = 0.6f),
        disabledGradientBottom = CrColors.TealDark.copy(alpha = 0.6f),
        disabledBorderBottom = CrColors.TealDark
    )
}

/**
 * Cyan variant matching CR's "Party!" button.
 */
@Composable
fun CrButtonCyan(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    icon: Painter? = null
) {
    CrButton(
        text = text,
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        icon = icon,
        gradientTop = CrColors.CyanLight,
        gradientBottom = CrColors.CyanDark,
        borderBottom = CrColors.CyanBorder,
        disabledGradientTop = CrColors.TealMid.copy(alpha = 0.6f),
        disabledGradientBottom = CrColors.TealDark.copy(alpha = 0.6f),
        disabledBorderBottom = CrColors.TealDark
    )
}

@Composable
private fun CrButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier,
    enabled: Boolean,
    icon: Painter?,
    gradientTop: Color,
    gradientBottom: Color,
    borderBottom: Color,
    disabledGradientTop: Color,
    disabledGradientBottom: Color,
    disabledBorderBottom: Color
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val topColor = if (enabled) gradientTop else disabledGradientTop
    val bottomColor = if (enabled) gradientBottom else disabledGradientBottom
    val shadowColor = if (enabled) borderBottom else disabledBorderBottom
    val textColor = if (enabled) CrColors.TextPrimary else CrColors.TextDim

    val yOffset by animateDpAsState(
        targetValue = if (isPressed) 3.dp else 0.dp,
        label = "buttonPress"
    )

    val shape = RoundedCornerShape(14.dp)
    val bottomEdgeHeight = 5.dp  // V2: thicker for visible 3D

    Box(
        modifier = modifier
            .fillMaxWidth()
            .offset { IntOffset(0, yOffset.roundToPx()) }
    ) {
        // Bottom shadow/border edge (3D raised effect)
        Box(
            modifier = Modifier
                .matchParentSize()
                .padding(top = bottomEdgeHeight)
                .clip(shape)
                .drawBehind {
                    drawRoundRect(
                        color = shadowColor,
                        cornerRadius = CornerRadius(14.dp.toPx())
                    )
                }
        )

        // Main button body with gradient + top highlight
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isPressed) 0.dp else bottomEdgeHeight)
                .clip(shape)
                .drawBehind {
                    // Main gradient fill
                    drawRoundRect(
                        brush = Brush.verticalGradient(
                            colors = if (isPressed) {
                                listOf(bottomColor, topColor)
                            } else {
                                listOf(topColor, bottomColor)
                            }
                        ),
                        cornerRadius = CornerRadius(14.dp.toPx())
                    )
                    // White highlight line at top (light reflection)
                    if (enabled) {
                        drawRoundRect(
                            color = Color.White.copy(alpha = 0.22f),
                            size = Size(size.width, 3.dp.toPx()),
                            cornerRadius = CornerRadius(14.dp.toPx())
                        )
                    }
                }
                .clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    enabled = enabled,
                    onClick = onClick
                )
                .padding(vertical = 18.dp, horizontal = 24.dp),  // V2: chunkier
            contentAlignment = Alignment.Center
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (icon != null) {
                    Icon(
                        painter = icon,
                        contentDescription = null,
                        tint = textColor,
                        modifier = Modifier.size(24.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                }
                Text(
                    text = text,
                    style = CrTypography.headlineSmall,
                    color = textColor,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
