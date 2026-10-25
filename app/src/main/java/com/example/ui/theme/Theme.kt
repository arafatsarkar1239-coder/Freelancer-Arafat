package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = PureWhite,
    primaryContainer = ElectricBlueContainer,
    onPrimaryContainer = PureWhite,
    secondary = CyanAccent,
    onSecondary = CharcoalBlack,
    secondaryContainer = CyanContainer,
    onSecondaryContainer = PureWhite,
    tertiary = CyanAccentVariant,
    onTertiary = PureWhite,
    background = CharcoalBlack,
    onBackground = TextPrimary,
    surface = DeepNavy,
    onSurface = TextPrimary,
    surfaceVariant = NavySurface,
    onSurfaceVariant = TextSecondary,
    outline = NavyBorder,
    outlineVariant = NavySurfaceElevated,
    error = ErrorRed,
    onError = PureWhite
)

private val LightColorScheme = DarkColorScheme // Primary dark-mode branding aesthetic requested

@Composable
fun FreelancerArafatTheme(
    darkTheme: Boolean = true, // Default to premium dark mode as specified
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = CharcoalBlack.toArgb()
                window.navigationBarColor = CharcoalBlack.toArgb()
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
                WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
