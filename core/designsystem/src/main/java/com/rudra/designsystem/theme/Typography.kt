package com.rudra.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object CareLogTypography {

    val material = Typography(
        titleMedium = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        ),
        bodyMedium = TextStyle(
            fontSize = 16.sp
        ),
        bodySmall = TextStyle(
            fontSize = 14.sp,
            color = CareLogColors.OnSurfaceVariant
        ),
        labelSmall = TextStyle(
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium
        )
    )
}
