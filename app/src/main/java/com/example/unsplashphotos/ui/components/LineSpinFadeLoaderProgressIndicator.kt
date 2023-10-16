package com.example.unsplashphotos.ui.components

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.progressSemantics
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin


private const val DefaultAnimationDuration = 1000
private val DefaultRectWidth = 3.dp
private val DefaultRectHeight = 12.dp
private val DefaultRectCornerRadius = 2.dp
private val DefaultDiameter = 40.dp
private const val DefaultMaxAlpha = 1f
private const val DefaultMinAlpha = .4f

@Composable
fun LineSpinFadeLoaderProgressIndicator(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.primary,
    animationDuration: Int = DefaultAnimationDuration,
    rectWidth: Dp = DefaultRectWidth,
    rectHeight: Dp = DefaultRectHeight,
    rectCornerRadius: Dp = DefaultRectCornerRadius,
    diameter: Dp = DefaultDiameter,
    maxAlpha: Float = DefaultMaxAlpha,
    minAlpha: Float = DefaultMinAlpha,
    isClockwise: Boolean = false
) {
    val transition = rememberInfiniteTransition()
    val fraction by transition.fraction(animationDuration)

    ProgressIndicator(modifier, diameter) {
        val alphaList = mutableListOf<Float>()
        for (i in 0..7) {
            val newFraction =
                getShiftedFraction(if (isClockwise) 1 - fraction else fraction, .1f * i)
            lerp(minAlpha, maxAlpha, newFraction).also { alphaList.add(it) }
        }

        drawIndeterminateLineSpinFadeLoaderIndicator(
            rectWidth = rectWidth.toPx(),
            rectHeight = rectHeight.toPx(),
            rectCornerRadius = rectCornerRadius.toPx(),
            alpha = alphaList,
            color = color
        )
    }
}

private fun DrawScope.drawIndeterminateLineSpinFadeLoaderIndicator(
    rectWidth: Float, rectHeight: Float, rectCornerRadius: Float, alpha: List<Float>, color: Color
) {

    for (i in 0..7) {
        val angle = PI.toFloat() / 4 * i
        val x = size.width * cos(angle) / 2
        val y = size.height * sin(angle) / 2

        rotate(
            degrees = (45f * i) + 90f, pivot = center + Offset(x, y)
        ) {
            drawRoundRect(
                color = color,
                size = Size(rectWidth, rectHeight),
                topLeft = center + Offset(-rectWidth / 2, 0f) + Offset(x, y),
                cornerRadius = CornerRadius(rectCornerRadius),
                alpha = alpha[i]
            )
        }
    }
}

@Composable
internal fun ProgressIndicator(
    modifier: Modifier, size: Dp, onDraw: DrawScope.() -> Unit
) {
    Canvas(
        modifier = modifier
            .progressSemantics()
            .size(size)
            .focusable(), onDraw = onDraw
    )
}

@Composable
internal fun InfiniteTransition.fraction(
    durationMillis: Int, delayMillis: Int = 0, easing: Easing = LinearEasing
): State<Float> {
    val duration = durationMillis + delayMillis

    return animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(animation = keyframes {
            this.durationMillis = duration
            0f at delayMillis / 2 with easing
            1f at durationMillis + (delayMillis / 2)
            1f at duration
        })
    )
}

internal fun lerp(start: Float, stop: Float, fraction: Float): Float {
    return start + ((stop - start) * fraction)
}

internal fun getShiftedFraction(fraction: Float, shift: Float): Float {
    val newFraction = (fraction + shift) % 1
    return (if (newFraction > .5) 1 - newFraction else newFraction) * 2
}
