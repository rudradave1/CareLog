package com.rudra.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun CareLogTheme(
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val colorScheme = if (isDark) {
        darkColorScheme(
            primary = CareLogColors.DarkPrimary,
            onPrimary = CareLogColors.DarkOnPrimary,
            primaryContainer = CareLogColors.DarkPrimaryContainer,
            onPrimaryContainer = CareLogColors.DarkOnPrimaryContainer,
            background = CareLogColors.DarkBackground,
            onBackground = CareLogColors.DarkOnBackground,
            surface = CareLogColors.DarkSurface,
            onSurface = CareLogColors.DarkOnSurface,
            surfaceVariant = CareLogColors.DarkSurfaceVariant,
            onSurfaceVariant = CareLogColors.DarkOnSurfaceVariant,
            outline = CareLogColors.DarkOutline,
            error = CareLogColors.DarkError,
            onError = CareLogColors.DarkOnError
        )
    } else {
        lightColorScheme(
            primary = CareLogColors.Primary,
            onPrimary = CareLogColors.OnPrimary,
            primaryContainer = CareLogColors.PrimaryContainer,
            onPrimaryContainer = CareLogColors.OnPrimaryContainer,
            background = CareLogColors.Background,
            onBackground = CareLogColors.OnBackground,
            surface = CareLogColors.Surface,
            onSurface = CareLogColors.OnSurface,
            surfaceVariant = CareLogColors.SurfaceVariant,
            onSurfaceVariant = CareLogColors.OnSurfaceVariant,
            outline = CareLogColors.Outline,
            error = CareLogColors.Error,
            onError = CareLogColors.OnError
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = CareLogTypography.material,
        shapes = CareLogShapes.material,
        content = content
    )
}
