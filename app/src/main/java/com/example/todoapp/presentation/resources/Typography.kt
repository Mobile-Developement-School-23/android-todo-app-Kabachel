package com.example.todoapp.presentation.resources

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.todoapp.R

/**
 * @author Данила Шабанов on 15.07.2023.
 */
val ExtendedAppTypography = ExtendedTypography(
    title = TextStyle(
        fontSize = 32.sp,
        fontWeight = FontWeight(500),
        color = Color(0xFF000000),
    ),
    subtitle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight(400),
        color = Color(0x4D000000),
    ),
    bodyTitle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontWeight = FontWeight(400),
        color = Color(0xFF000000),
    ),
    bodySubtitle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight(400),
        color = Color(0x4D000000),
    ),
    button = TextStyle(
        fontSize = 14.sp,
        lineHeight = 24.sp,
        fontWeight = FontWeight(500),
        color = Color(0xFF007AFF),
        textAlign = TextAlign.Center,
        letterSpacing = 0.16.sp,
    )
)