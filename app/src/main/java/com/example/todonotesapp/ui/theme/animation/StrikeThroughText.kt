package com.example.todonotesapp.ui.theme.animation

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun StrikeThroughText(
    text: String,
    isDone: Boolean,
    modifier: Modifier = Modifier,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    color: Color = Color.Black,
    lineColor: Color = Color.Black,
    lineThickness: Float = 5f,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip
) {
    val progress by animateFloatAsState(
        targetValue = if (isDone) 1f else 0f,
        animationSpec = tween(durationMillis = 400),
        label = "StrikeThroughAnimation"
    )

    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }

    Box(modifier = modifier.padding(5.dp)) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = color,
            maxLines = maxLines,
            overflow = overflow,
            onTextLayout = { textLayoutResult = it }
        )
        Canvas(modifier = Modifier.matchParentSize()) {
            textLayoutResult?.let { layoutResult ->
                for (i in 0 until layoutResult.lineCount) {
                    val y = (layoutResult.getLineTop(i) + layoutResult.getLineBottom(i)) / 2
                    val startX = layoutResult.getLineLeft(i)
                    val endX = layoutResult.getLineRight(i)
                    val animatedEndX = startX + (endX - startX) * progress

                    drawLine(
                        color = lineColor,
                        start = Offset(startX, y),
                        end = Offset(animatedEndX, y),
                        strokeWidth = lineThickness
                    )
                }
            }
        }
    }
}
