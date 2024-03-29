package fr.deuspheara.eshopapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val LightColorScheme = darkColorScheme(
    primary = SaddleBrown,
    onPrimary = Gold,
    secondary = Gold,
    tertiary = BlackChocolate,
    background = WhiteChocolate,
    onBackground = SaddleBrown,
    surface = WhiteChocolate,
    onSurface = SaddleBrown,
    surfaceVariant = LightGold,
    onSurfaceVariant = SaddleBrown,
    outline = Gold,
    error = RedCrayola,
    onError = Color.White
)

private val DarkColorScheme = lightColorScheme(
    primary = SaddleBrown,
    onPrimary = Gold,
    secondary = Gold,
    tertiary = BlackChocolate,
    background = WhiteChocolate,
    onBackground = SaddleBrown,
    surface = WhiteChocolate,
    onSurface = SaddleBrown,
    surfaceVariant = LightGold,
    onSurfaceVariant = SaddleBrown,
    outline = Gold,
    error = RedCrayola,
    onError = Color.White

)

val ColorScheme.customGreen: Color
    @Composable get() = if (!isSystemInDarkTheme()) Green600 else Green200

val ColorScheme.customRed: Color
    @Composable get() = RedCrayola

@Composable
fun ShopAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()

            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}