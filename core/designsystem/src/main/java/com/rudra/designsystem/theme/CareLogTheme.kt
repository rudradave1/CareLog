package com.rudra.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

@Composable
fun CareLogTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = CareLogColors.Primary,
            onPrimary = CareLogColors.OnPrimary,

            background = CareLogColors.Background,
            onBackground = CareLogColors.OnBackground,

            surface = CareLogColors.Surface,
            onSurface = CareLogColors.OnSurface,

            surfaceVariant = CareLogColors.SurfaceVariant,
            onSurfaceVariant = CareLogColors.OnSurfaceVariant
        ),
        typography = CareLogTypography.material,
        content = content
    )
}
