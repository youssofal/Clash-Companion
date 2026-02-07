package com.yoyostudios.clashcompanion.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

/**
 * Material 3 dark scheme wired to CR teal palette.
 */
private val CrDarkColorScheme = darkColorScheme(
    primary = CrColors.Gold,
    onPrimary = CrColors.TextPrimary,
    primaryContainer = CrColors.GoldDark,
    onPrimaryContainer = CrColors.TextPrimary,

    secondary = CrColors.Cyan,
    onSecondary = CrColors.TextPrimary,
    secondaryContainer = CrColors.CyanDark,
    onSecondaryContainer = CrColors.TextPrimary,

    tertiary = CrColors.SmartPurple,
    onTertiary = CrColors.TextPrimary,

    background = CrColors.TealMid,
    onBackground = CrColors.TextPrimary,

    surface = CrColors.TealDark,
    onSurface = CrColors.TextPrimary,
    surfaceVariant = CrColors.TealMid,
    onSurfaceVariant = CrColors.TextSecondary,

    error = CrColors.Error,
    onError = CrColors.TextPrimary,

    outline = CrColors.TealBorder,
    outlineVariant = CrColors.TealBorder.copy(alpha = 0.5f)
)

@Composable
fun ClashCompanionTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalSpacing provides Spacing()
    ) {
        MaterialTheme(
            colorScheme = CrDarkColorScheme,
            typography = CrTypography,
            content = content
        )
    }
}
