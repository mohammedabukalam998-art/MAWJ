package com.mawjj.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val MAWJDarkColors = darkColorScheme()

@Composable
fun MAWJTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = MAWJDarkColors,
        content = content
    )
}
