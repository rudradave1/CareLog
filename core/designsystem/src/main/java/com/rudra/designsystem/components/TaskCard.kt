package com.rudra.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.rudra.designsystem.theme.CareLogTypography
import com.rudra.designsystem.theme.Spacing

@Composable
fun TaskCard(
    title: String,
    subtitle: String
) {
    Card(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.xs)
    ) {
        Column(
            modifier = Modifier.padding(Spacing.md)
        ) {
            Text(
                text = title,
                style = CareLogTypography.title
            )

            Spacer(Modifier.height(Spacing.xs))

            Text(
                text = subtitle,
                style = CareLogTypography.body
            )
        }
    }
}
