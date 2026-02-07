package com.yoyostudios.clashcompanion.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yoyostudios.clashcompanion.ui.theme.CrColors

/**
 * Chunky filled lightning bolt — Fast Path tier icon.
 * Green gradient fill with subtle glow.
 */
@Composable
fun TierIconFast(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val path = Path().apply {
            moveTo(w * 0.55f, 0f)
            lineTo(w * 0.25f, h * 0.45f)
            lineTo(w * 0.45f, h * 0.45f)
            lineTo(w * 0.38f, h)
            lineTo(w * 0.78f, h * 0.48f)
            lineTo(w * 0.55f, h * 0.48f)
            lineTo(w * 0.7f, 0f)
            close()
        }
        // Glow
        drawPath(path, color = CrColors.FastGreen.copy(alpha = 0.3f), style = Stroke(width = 4f))
        // Fill with gradient
        drawPath(
            path,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF86EFAC), CrColors.FastGreen)
            )
        )
    }
}

/**
 * Filled stacked cards — Queue Path tier icon.
 * Cyan gradient, cards overlapping for depth.
 */
@Composable
fun TierIconQueue(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val cardW = w * 0.6f
        val cardH = h * 0.7f
        val r = w * 0.06f

        // Back card (offset)
        drawRoundRect(
            color = CrColors.QueueCyan.copy(alpha = 0.5f),
            topLeft = Offset(w * 0.3f, h * 0.05f),
            size = Size(cardW, cardH),
            cornerRadius = CornerRadius(r)
        )
        // Middle card
        drawRoundRect(
            color = CrColors.QueueCyan.copy(alpha = 0.7f),
            topLeft = Offset(w * 0.18f, h * 0.15f),
            size = Size(cardW, cardH),
            cornerRadius = CornerRadius(r)
        )
        // Front card
        drawRoundRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFA5F3FC), CrColors.QueueCyan)
            ),
            topLeft = Offset(w * 0.06f, h * 0.25f),
            size = Size(cardW, cardH),
            cornerRadius = CornerRadius(r)
        )
        // Border on front card
        drawRoundRect(
            color = Color.White.copy(alpha = 0.4f),
            topLeft = Offset(w * 0.06f, h * 0.25f),
            size = Size(cardW, cardH),
            cornerRadius = CornerRadius(r),
            style = Stroke(width = 1.5f)
        )
    }
}

/**
 * Filled crosshair/scope — Targeting Path tier icon.
 * Gold gradient, thick rings, solid center.
 */
@Composable
fun TierIconTarget(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val center = Offset(this.size.width / 2, this.size.height / 2)
        val outerR = this.size.minDimension * 0.45f
        val innerR = this.size.minDimension * 0.2f
        val dotR = this.size.minDimension * 0.08f
        val strokeW = this.size.minDimension * 0.08f

        // Outer ring
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(CrColors.GoldLight, CrColors.TargetGold),
                center = center
            ),
            radius = outerR,
            center = center,
            style = Stroke(width = strokeW)
        )
        // Inner ring
        drawCircle(
            color = CrColors.TargetGold,
            radius = innerR,
            center = center,
            style = Stroke(width = strokeW * 0.7f)
        )
        // Center dot
        drawCircle(
            color = CrColors.GoldLight,
            radius = dotR,
            center = center
        )
        // Crosshair lines
        val lineLen = outerR * 0.35f
        val gap = outerR + strokeW
        listOf(
            Offset(center.x, center.y - gap) to Offset(center.x, center.y - gap - lineLen),
            Offset(center.x, center.y + gap) to Offset(center.x, center.y + gap + lineLen),
            Offset(center.x - gap, center.y) to Offset(center.x - gap - lineLen, center.y),
            Offset(center.x + gap, center.y) to Offset(center.x + gap + lineLen, center.y),
        ).forEach { (start, end) ->
            drawLine(
                color = CrColors.TargetGold,
                start = start,
                end = end,
                strokeWidth = strokeW * 0.7f,
                cap = StrokeCap.Round
            )
        }
    }
}

/**
 * Filled crown — Smart Path tier icon.
 * Purple gradient, like CR's trophy but purple.
 */
@Composable
fun TierIconSmart(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val path = Path().apply {
            // Crown shape
            moveTo(w * 0.1f, h * 0.75f)
            lineTo(w * 0.1f, h * 0.35f)
            lineTo(w * 0.3f, h * 0.5f)
            lineTo(w * 0.5f, h * 0.15f)
            lineTo(w * 0.7f, h * 0.5f)
            lineTo(w * 0.9f, h * 0.35f)
            lineTo(w * 0.9f, h * 0.75f)
            close()
        }
        // Glow
        drawPath(path, color = CrColors.SmartPurple.copy(alpha = 0.25f), style = Stroke(width = 3f))
        // Fill
        drawPath(
            path,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFE9D5FF), CrColors.SmartPurple)
            )
        )
        // Base bar
        drawRoundRect(
            color = CrColors.SmartPurple,
            topLeft = Offset(w * 0.08f, h * 0.75f),
            size = Size(w * 0.84f, h * 0.12f),
            cornerRadius = CornerRadius(2f)
        )
        // Jewel dots on crown points
        listOf(w * 0.3f, w * 0.5f, w * 0.7f).forEach { x ->
            drawCircle(
                color = Color.White.copy(alpha = 0.7f),
                radius = w * 0.04f,
                center = Offset(x, h * 0.48f)
            )
        }
    }
}

/**
 * Filled shield with clock — Conditional Path tier icon.
 * Orange gradient, chunky shape.
 */
@Composable
fun TierIconConditional(modifier: Modifier = Modifier, size: Dp = 20.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        // Shield shape
        val shield = Path().apply {
            moveTo(w * 0.5f, h * 0.05f)
            lineTo(w * 0.1f, h * 0.25f)
            lineTo(w * 0.1f, h * 0.55f)
            quadraticTo(w * 0.1f, h * 0.85f, w * 0.5f, h * 0.98f)
            quadraticTo(w * 0.9f, h * 0.85f, w * 0.9f, h * 0.55f)
            lineTo(w * 0.9f, h * 0.25f)
            close()
        }
        // Glow
        drawPath(shield, color = CrColors.ConditionalOrange.copy(alpha = 0.25f), style = Stroke(width = 3f))
        // Fill
        drawPath(
            shield,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFFED7AA), CrColors.ConditionalOrange)
            )
        )
        // Clock circle inside shield
        val clockCenter = Offset(w * 0.5f, h * 0.52f)
        val clockR = w * 0.2f
        drawCircle(color = Color.White.copy(alpha = 0.9f), radius = clockR, center = clockCenter)
        // Clock hands
        drawLine(
            color = CrColors.ConditionalOrange,
            start = clockCenter,
            end = Offset(clockCenter.x, clockCenter.y - clockR * 0.65f),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )
        drawLine(
            color = CrColors.ConditionalOrange,
            start = clockCenter,
            end = Offset(clockCenter.x + clockR * 0.5f, clockCenter.y),
            strokeWidth = 2.5f,
            cap = StrokeCap.Round
        )
    }
}

/**
 * Golden crown icon for the app header.
 * Like TierIconSmart but gold-colored and larger.
 */
@Composable
fun CrCrown(modifier: Modifier = Modifier, size: Dp = 48.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val crown = Path().apply {
            moveTo(w * 0.08f, h * 0.78f)
            lineTo(w * 0.08f, h * 0.30f)
            lineTo(w * 0.28f, h * 0.48f)
            lineTo(w * 0.50f, h * 0.10f)
            lineTo(w * 0.72f, h * 0.48f)
            lineTo(w * 0.92f, h * 0.30f)
            lineTo(w * 0.92f, h * 0.78f)
            close()
        }
        // Golden glow
        drawPath(crown, color = CrColors.Gold.copy(alpha = 0.35f), style = Stroke(width = 6f))
        // Fill with gold gradient
        drawPath(
            crown,
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFFFD740), CrColors.Gold, CrColors.GoldDark)
            )
        )
        // Base bar
        drawRoundRect(
            brush = Brush.verticalGradient(
                colors = listOf(CrColors.Gold, CrColors.GoldDark)
            ),
            topLeft = Offset(w * 0.06f, h * 0.78f),
            size = Size(w * 0.88f, h * 0.12f),
            cornerRadius = CornerRadius(3f)
        )
        // Jewel dots
        val jewelColor = Color.White.copy(alpha = 0.85f)
        drawCircle(color = jewelColor, radius = w * 0.045f, center = Offset(w * 0.28f, h * 0.46f))
        drawCircle(color = jewelColor, radius = w * 0.055f, center = Offset(w * 0.50f, h * 0.28f))
        drawCircle(color = jewelColor, radius = w * 0.045f, center = Offset(w * 0.72f, h * 0.46f))
    }
}

/**
 * Elixir drop icon matching CR's pink/purple elixir visual.
 * Teardrop shape with pink-to-purple gradient.
 */
@Composable
fun ElixirDrop(modifier: Modifier = Modifier, size: Dp = 18.dp) {
    Canvas(modifier = modifier.size(size)) {
        val w = this.size.width
        val h = this.size.height
        val drop = Path().apply {
            // Teardrop: pointed top, round bottom
            moveTo(w * 0.5f, h * 0.05f)
            cubicTo(
                w * 0.5f, h * 0.05f,
                w * 0.15f, h * 0.50f,
                w * 0.15f, h * 0.65f
            )
            cubicTo(
                w * 0.15f, h * 0.88f,
                w * 0.35f, h * 0.98f,
                w * 0.5f, h * 0.98f
            )
            cubicTo(
                w * 0.65f, h * 0.98f,
                w * 0.85f, h * 0.88f,
                w * 0.85f, h * 0.65f
            )
            cubicTo(
                w * 0.85f, h * 0.50f,
                w * 0.5f, h * 0.05f,
                w * 0.5f, h * 0.05f
            )
            close()
        }
        // Fill with pink-to-purple gradient
        drawPath(
            drop,
            brush = Brush.verticalGradient(
                colors = listOf(CrColors.ElixirPink, CrColors.ElixirPurple)
            )
        )
        // Highlight
        drawCircle(
            color = Color.White.copy(alpha = 0.4f),
            radius = w * 0.12f,
            center = Offset(w * 0.40f, h * 0.58f)
        )
    }
}
