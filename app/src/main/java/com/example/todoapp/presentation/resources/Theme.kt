package com.example.todoapp.presentation.resources

/**
 * @author Данила Шабанов on 15.07.2023.
 */
import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.TextStyle
import androidx.core.view.WindowCompat
import com.example.todoapp.presentation.resources.TodoAppColor.DarkBackElevated
import com.example.todoapp.presentation.resources.TodoAppColor.DarkBackPrimary
import com.example.todoapp.presentation.resources.TodoAppColor.DarkBackSecondary
import com.example.todoapp.presentation.resources.TodoAppColor.DarkLabelDisable
import com.example.todoapp.presentation.resources.TodoAppColor.DarkLabelPrimary
import com.example.todoapp.presentation.resources.TodoAppColor.DarkLabelSecondary
import com.example.todoapp.presentation.resources.TodoAppColor.DarkLabelTertiary
import com.example.todoapp.presentation.resources.TodoAppColor.DarkSupportOverlay
import com.example.todoapp.presentation.resources.TodoAppColor.DarkSupportSeparator
import com.example.todoapp.presentation.resources.TodoAppColor.LightBackElevated
import com.example.todoapp.presentation.resources.TodoAppColor.LightBackPrimary
import com.example.todoapp.presentation.resources.TodoAppColor.LightBackSecondary
import com.example.todoapp.presentation.resources.TodoAppColor.LightLabelDisable
import com.example.todoapp.presentation.resources.TodoAppColor.LightLabelPrimary
import com.example.todoapp.presentation.resources.TodoAppColor.LightLabelSecondary
import com.example.todoapp.presentation.resources.TodoAppColor.LightLabelTertiary
import com.example.todoapp.presentation.resources.TodoAppColor.LightSupportOverlay
import com.example.todoapp.presentation.resources.TodoAppColor.LightSupportSeparator

@Immutable
data class ExtendedColors(
    val supportSeparator: Color = Color.Unspecified,
    val supportOverlay: Color = Color.Unspecified,
    val labelPrimary: Color = Color.Unspecified,
    val labelSecondary: Color = Color.Unspecified,
    val labelTertiary: Color = Color.Unspecified,
    val labelDisable: Color = Color.Unspecified,
    val labelPrimaryReversed: Color = Color.Unspecified,
    val backPrimary: Color = Color.Unspecified,
    val backSecondary: Color = Color.Unspecified,
    val backElevated: Color = Color.Unspecified
)

@Immutable
data class ExtendedTypography(
    val title: TextStyle = TextStyle.Default,
    val subtitle: TextStyle = TextStyle.Default,
    val bodyTitle: TextStyle = TextStyle.Default,
    val bodySubtitle: TextStyle = TextStyle.Default,
    val button: TextStyle = TextStyle.Default,
)

val lightExtendedColors = ExtendedColors(
    supportSeparator = LightSupportSeparator,
    supportOverlay = LightSupportOverlay,
    labelPrimary = LightLabelPrimary,
    labelPrimaryReversed = DarkLabelPrimary,
    labelSecondary = LightLabelSecondary,
    labelTertiary = LightLabelTertiary,
    labelDisable = LightLabelDisable,
    backPrimary = LightBackPrimary,
    backSecondary = LightBackSecondary,
    backElevated = LightBackElevated
)

val darkExtendedColors = ExtendedColors(
    supportSeparator = DarkSupportSeparator,
    supportOverlay = DarkSupportOverlay,
    labelPrimary = DarkLabelPrimary,
    labelPrimaryReversed = LightLabelPrimary,
    labelSecondary = DarkLabelSecondary,
    labelTertiary = DarkLabelTertiary,
    labelDisable = DarkLabelDisable,
    backPrimary = DarkBackPrimary,
    backSecondary = DarkBackSecondary,
    backElevated = DarkBackElevated
)

val LocalExtendedColors = staticCompositionLocalOf { ExtendedColors() }
val LocalExtendedTypography = staticCompositionLocalOf { ExtendedTypography() }

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val extendedColors =
        if (darkTheme) darkExtendedColors else lightExtendedColors
    val extendedTypography = ExtendedAppTypography

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> darkColorScheme()
        else -> lightColorScheme()
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            window.statusBarColor = android.graphics.Color.TRANSPARENT
        }
    }

    CompositionLocalProvider(
        LocalExtendedColors provides extendedColors,
        LocalExtendedTypography provides extendedTypography
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography(),
            content = content
        )
    }
}

object ExtendedTheme {
    val colors: ExtendedColors
        @Composable
        get() = LocalExtendedColors.current

    val typography: ExtendedTypography
        @Composable
        get() = LocalExtendedTypography.current
}