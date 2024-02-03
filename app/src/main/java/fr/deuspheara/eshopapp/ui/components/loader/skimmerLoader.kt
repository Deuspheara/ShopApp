package fr.deuspheara.eshopapp.ui.components.loader

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape

/**
 * _Eshopapp_
 *
 * fr.deuspheara.eshopapp.ui.components.loader.skimmerLoader
 *
 * ### Information
 * - __Author__ Deuspheara
 *
 * ### Description
 * Skeleton loader
 *
 */
@Composable
fun Modifier.skeletonLoader(
    colors: Pair<Color, Color> = MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.primaryContainer,
    isLoading: Boolean = true,
    shape: Shape = RectangleShape,
) = if (isLoading) this.composed {
    val transition = rememberInfiniteTransition(label = "loading-transition")
    val colorAnimation by transition.animateColor(
        label = "loading-animation",
        initialValue = colors.first,
        targetValue = colors.second,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutLinearInEasing,
            ),
            repeatMode = RepeatMode.Reverse,
        )
    )
    background(color = colorAnimation, shape)
} else this