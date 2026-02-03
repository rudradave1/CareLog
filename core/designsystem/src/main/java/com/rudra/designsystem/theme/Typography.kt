package com.rudra.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object CareLogTypography {

    val material = Typography(
        displaySmall = TextStyle(
            fontSize = 30.sp,
            fontWeight = FontWeight.SemiBold
        ),
        headlineSmall = TextStyle(
            fontSize = 22.sp,
            fontWeight = FontWeight.SemiBold
        ),
        titleMedium = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        ),
        titleSmall = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        bodyMedium = TextStyle(
            fontSize = 16.sp
        ),
        bodySmall = TextStyle(
            fontSize = 14.sp,
            color = CareLogColors.OnSurfaceVariant
        ),
        labelLarge = TextStyle(
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        ),
        labelSmall = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    )
}
