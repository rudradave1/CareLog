package com.rudra.designsystem.theme

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.staticCompositionLocalOf

val LocalSnackbarHostState =
    staticCompositionLocalOf<SnackbarHostState> {
        error("SnackbarHostState not provided")
    }