package com.mugiwara.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFFF6B35),
    onPrimary = Color(0xFF000000),
    primaryContainer = Color(0xFFE55A2B),
    onPrimaryContainer = Color(0xFFFFFFFF),
    secondary = Color(0xFFFF8C42),
    onSecondary = Color(0xFF000000),
    secondaryContainer = Color(0xFFFFA726),
    onSecondaryContainer = Color(0xFF000000),
    tertiary = Color(0xFFFFAB91),
    onTertiary = Color(0xFF000000),
    background = Color(0xFF121212),
    onBackground = Color(0xFFFFFFFF),
    surface = Color(0xFF1E1E1E),
    onSurface = Color(0xFFFFFFFF),
    surfaceVariant = Color(0xFF2D2D2D),
    onSurfaceVariant = Color(0xFFB0B0B0),
    error = Color(0xFFCF6679),
    onError = Color(0xFF000000),
    outline = Color(0xFF494949)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFFF6B35),
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFE0D6),
    onPrimaryContainer = Color(0xFF3E1A0F),
    secondary = Color(0xFFFF8C42),
    onSecondary = Color(0xFFFFFFFF),
    background = Color(0xFFFFFBFE),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFFFFBFE),
    onSurface = Color(0xFF1C1B1F)
)

@Composable
fun MugiwaraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
