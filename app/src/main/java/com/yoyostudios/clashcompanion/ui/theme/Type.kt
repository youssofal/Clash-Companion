package com.yoyostudios.clashcompanion.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yoyostudios.clashcompanion.R

/**
 * Dual-font typography with CR-style text shadows.
 * - Luckiest Guy: chunky display font (closest free match to Supercell-Magic)
 * - Inter: clean body/label/overlay font for readability
 */

val LuckiestGuyFamily = FontFamily(
    Font(R.font.luckiest_guy, FontWeight.Normal)
)

val InterFamily = FontFamily(
    Font(R.font.inter_regular, FontWeight.Normal),
    // Variable font — Compose will handle weight interpolation
    Font(R.font.inter_regular, FontWeight.Medium),
    Font(R.font.inter_regular, FontWeight.SemiBold),
    Font(R.font.inter_regular, FontWeight.Bold),
)

/**
 * CR applies drop shadows to all display/header text.
 * This shadow gives the 3D depth visible in CR's "Battle" and "Party!" buttons.
 */
val CrTextShadow = Shadow(
    color = Color.Black.copy(alpha = 0.5f),
    offset = Offset(2f, 3f),
    blurRadius = 4f
)

val CrTypography = Typography(
    // Luckiest Guy — display/headers
    displayLarge = TextStyle(
        fontFamily = LuckiestGuyFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 34.sp,
        letterSpacing = 1.sp,
        shadow = CrTextShadow
    ),
    headlineMedium = TextStyle(
        fontFamily = LuckiestGuyFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        letterSpacing = 0.5.sp,
        shadow = CrTextShadow
    ),
    headlineSmall = TextStyle(
        fontFamily = LuckiestGuyFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        letterSpacing = 0.5.sp,
        shadow = CrTextShadow
    ),

    // Inter — body, labels, overlay text
    titleMedium = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelLarge = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = InterFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp
    )
)
