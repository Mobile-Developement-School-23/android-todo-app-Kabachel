package com.example.todoapp.presentation.utils

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

/**
 * @author Данила Шабанов on 14.07.2023.
 */
@Composable
fun EmptySpacer(size: Dp) {
    Spacer(modifier = Modifier.padding(size))
}