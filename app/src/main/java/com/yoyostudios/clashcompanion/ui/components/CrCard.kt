package com.yoyostudios.clashcompanion.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yoyostudios.clashcompanion.ui.theme.CrColors

/**
 * A reusable card matching CR's panel style.
 *
 * - crTealDark background
 * - Thick visible border (2dp, tealBorder color)
 * - Chunky rounded corners (16dp)
 * - Optional gold border variant for highlighted cards
 */
@Composable
fun CrCard(
    modifier: Modifier = Modifier,
    borderColor: Color = CrColors.TealBorder,
    borderWidth: Dp = 2.dp,
    cornerRadius: Dp = 16.dp,
    backgroundColor: Color = CrColors.TealDark,
    contentPadding: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius),
        color = backgroundColor,
        border = BorderStroke(borderWidth, borderColor)
    ) {
        Box(modifier = Modifier.padding(contentPadding)) {
            content()
        }
    }
}

/**
 * Gold-bordered variant for highlighted/important cards.
 */
@Composable
fun CrCardGold(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CrCard(
        modifier = modifier,
        borderColor = CrColors.GoldDark,
        content = content
    )
}

/**
 * Purple-bordered variant for Smart Path / Opus sections.
 */
@Composable
fun CrCardPurple(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    CrCard(
        modifier = modifier,
        borderColor = CrColors.SmartPurple.copy(alpha = 0.7f),
        content = content
    )
}
