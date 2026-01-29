package com.rudra.designsystem.theme

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CareLogScaffold(
    title: String,
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) }
            )
        },
        floatingActionButton = {
            floatingActionButton?.invoke()
        },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .padding(Spacing.md)
        ) {
            CompositionLocalProvider(
                LocalSnackbarHostState provides snackbarHostState
            ) {
                content()
            }
        }
    }
}
