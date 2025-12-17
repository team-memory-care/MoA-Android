package com.moa.app.feature.senior.quiz.attention.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun KeyPadContent(
    input: String,
    onInputChanged: (String) -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    maxInputLength: Int = 2,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            repeat(maxInputLength) { index ->
                val char = input.getOrNull(index)?.toString() ?: ""
                val isFilled = char.isNotEmpty()

                KeyPadButton(
                    text = char,
                    enabled = isFilled,
                    onClick = {},
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        DeleteButton(
            enabled = input.isNotEmpty(),
            onClick = onDeleteClick,
        )

        Spacer(modifier = Modifier.height(28.dp))

        NumericKeyPad(onNumberClick = onInputChanged)
    }
}

@Composable
private fun KeyPadButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
) {
    val roundedCornerShape = RoundedCornerShape(12.dp)
    val backgroundColor = if (enabled) MoaTheme.colors.white else MoaTheme.colors.coolGray98

    Box(
        modifier = modifier
            .clip(roundedCornerShape)
            .background(color = backgroundColor)
            .run {
                if (enabled) {
                    this.border(
                        width = 2.dp,
                        color = MoaTheme.colors.coolGray97,
                        shape = roundedCornerShape,
                    )
                } else {
                    this
                }
            }
            .clickable(enabled = enabled, onClick = onClick)
            .padding(vertical = 16.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.display2Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.defaultMinSize(minWidth = 20.dp, minHeight = 36.dp),
        )
    }
}

@Composable
private fun DeleteButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
) {
    val backgroundColor = if (enabled) MoaTheme.colors.red50 else MoaTheme.colors.coolGray99
    val contentColor = if (enabled) MoaTheme.colors.red500 else MoaTheme.colors.coolGray80
    val borderColor = if (enabled) MoaTheme.colors.red300 else MoaTheme.colors.coolGray97
    val roundedCornerShape = RoundedCornerShape(12.dp)

    Box(
        modifier = modifier
            .clip(roundedCornerShape)
            .background(color = backgroundColor)
            .border(
                width = 2.dp,
                color = borderColor,
                shape = roundedCornerShape
            )
            .clickable(enabled = enabled, onClick = onClick)
            .padding(10.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = "지우기",
            color = contentColor,
            style = MoaTheme.typography.title2Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 36.dp),
        )
    }
}

@Composable
private fun NumericKeyPad(
    onNumberClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val numberList = remember { (0..9).map { it.toString() } }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        numberList.chunked(5).forEach { rowNumbers ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                rowNumbers.forEach { numberStr ->
                    KeyPadButton(
                        text = numberStr,
                        onClick = { onNumberClick(numberStr) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    val input = remember { mutableStateOf("") }
    val setInput = { value: String -> input.value = value }

    KeyPadContent(
        input = input.value,
        onInputChanged = setInput,
        onDeleteClick = { setInput("") },
    )
}
