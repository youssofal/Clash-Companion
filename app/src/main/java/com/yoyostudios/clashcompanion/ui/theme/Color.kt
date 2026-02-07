package com.yoyostudios.clashcompanion.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * Clash Royale-authentic color palette.
 * Derived from direct visual analysis of CR's main menu UI.
 * V2: boosted saturation on buttons for visible 3D effect.
 */
object CrColors {

    // ── Background family (warm teal-blue, NOT cold navy) ──
    val TealDark = Color(0xFF0E4B5A)
    val TealMid = Color(0xFF1A7F8F)
    val TealLight = Color(0xFF2596A8)
    val TealBorder = Color(0xFF2BA5B8)

    // ── Primary action: orange-gold gradient (CR's "Battle" button) ──
    // V2: MORE saturated for visible 3D
    val GoldLight = Color(0xFFFFC040)     // Brighter top
    val Gold = Color(0xFFF5A623)
    val GoldDark = Color(0xFFCC7A10)      // Darker bottom
    val GoldBorder = Color(0xFFA06010)    // Much darker 3D edge

    // ── Secondary action: light cyan (CR's "Party!" button) ──
    // V2: MORE saturated
    val CyanLight = Color(0xFF60D0F0)     // Brighter top
    val Cyan = Color(0xFF3DB8D9)
    val CyanDark = Color(0xFF1A7090)      // Darker bottom
    val CyanBorder = Color(0xFF125A70)    // 3D edge

    // ── Tier colors (tuned for teal backgrounds) ──
    val FastGreen = Color(0xFF4ADE80)
    val QueueCyan = Color(0xFF67E8F9)
    val TargetGold = Color(0xFFF5A623)
    val SmartPurple = Color(0xFFC084FC)
    val ConditionalOrange = Color(0xFFFB923C)

    // ── Status ──
    val Green = Color(0xFF4ADE80)
    val Error = Color(0xFFEF4444)
    val MicActive = Color(0xFF22D3EE)

    // ── Elixir (CR's pink/purple elixir drop) ──
    val ElixirPink = Color(0xFFE84393)
    val ElixirPurple = Color(0xFFA855F7)

    // ── Text ──
    val TextPrimary = Color(0xFFFFFFFF)
    val TextSecondary = Color(0xFFB0D4E0)
    val TextGold = Color(0xFFFFD740)
    val TextDim = Color(0xFF5A8A98)
}
