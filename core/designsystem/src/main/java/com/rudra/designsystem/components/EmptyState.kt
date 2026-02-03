package com.rudra.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import com.rudra.designsystem.theme.Spacing

@Composable
fun EmptyState(
    message: String,
    title: String = "You're all clear"
) {
    val context = LocalContext.current
    val motionScale = remember {
        Settings.Global.getFloat(
            context.contentResolver,
            Settings.Global.ANIMATOR_DURATION_SCALE,
            1f
        )
    }
    val alpha = remember { Animatable(0f) }
    val offset = remember { Animatable(6f) }

    LaunchedEffect(Unit) {
        val duration = if (motionScale == 0f) 0 else 300
        alpha.animateTo(1f, animationSpec = tween(duration))
        offset.animateTo(0f, animationSpec = tween(duration))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.lg),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier
                .alpha(alpha.value)
                .graphicsLayer(translationY = offset.value)
        )

        Spacer(modifier = Modifier.height(Spacing.sm))

        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .alpha(alpha.value)
                .graphicsLayer(translationY = offset.value)
        )
    }
}
