package com.yoyostudios.clashcompanion.ui.components

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import com.yoyostudios.clashcompanion.ui.theme.CrColors

/**
 * Tiled rectangular background matching CR's main menu.
 *
 * CR's background is a grid of rectangular tiles with visible
 * darker border lines between them. Each tile has an internal
 * diamond/crosshatch pattern with alternating light/dark fills.
 */
fun Modifier.crBackground(): Modifier = this.drawBehind {
    val w = size.width
    val h = size.height

    // Base fill
    drawRect(color = CrColors.TealMid)

    val tileSize = 80f              // Each rectangular tile
    val tileBorderWidth = 1.5f      // Visible border between tiles
    val tileBorderColor = Color(0xFF147080)  // Darker than TealMid
    val diamondSize = tileSize / 2  // Two diamonds per tile edge

    val lightFill = Color(0xFF22A0B2).copy(alpha = 0.30f)
    val darkFill = Color(0xFF0D5560).copy(alpha = 0.25f)

    // Draw tile grid
    var tileY = 0f
    var tileRow = 0
    while (tileY < h + tileSize) {
        var tileX = 0f
        var tileCol = 0
        while (tileX < w + tileSize) {

            // Diamond crosshatch inside this tile
            val halfD = diamondSize / 2
            for (dy in 0..1) {
                for (dx in 0..1) {
                    val cx = tileX + dx * diamondSize + halfD
                    val cy = tileY + dy * diamondSize + halfD
                    val isLight = (tileRow + tileCol + dx + dy) % 2 == 0
                    val diamond = Path().apply {
                        moveTo(cx, cy - halfD)
                        lineTo(cx + halfD, cy)
                        lineTo(cx, cy + halfD)
                        lineTo(cx - halfD, cy)
                        close()
                    }
                    drawPath(
                        path = diamond,
                        color = if (isLight) lightFill else darkFill
                    )
                }
            }

            tileX += tileSize
            tileCol++
        }
        tileY += tileSize
        tileRow++
    }

    // Draw tile border grid lines (horizontal)
    var lineY = 0f
    while (lineY <= h) {
        drawLine(
            color = tileBorderColor,
            start = Offset(0f, lineY),
            end = Offset(w, lineY),
            strokeWidth = tileBorderWidth
        )
        lineY += tileSize
    }

    // Draw tile border grid lines (vertical)
    var lineX = 0f
    while (lineX <= w) {
        drawLine(
            color = tileBorderColor,
            start = Offset(lineX, 0f),
            end = Offset(lineX, h),
            strokeWidth = tileBorderWidth
        )
        lineX += tileSize
    }
}
