package com.moa.app.designsystem.component.core.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun MaOtpTextField(
    otpText: String,
    onOtpTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    otpCount: Int = 4,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource? = null,
    placeholder: String = "0",
    autoHideKeyboard: Boolean = true,
    onComplete: (() -> Unit)? = null,
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(otpText) {
        if (otpText.length == otpCount) {
            if (autoHideKeyboard) {
                focusManager.clearFocus()
            }
            onComplete?.invoke()
        }
    }

    BasicTextField(
        value = otpText,
        onValueChange = {
            if (it.length <= otpCount && it.all { char -> char.isDigit() }) {
                onOtpTextChange(it)
            }
        },
        modifier = modifier.fillMaxWidth(),
        enabled = enabled,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource,
        decorationBox = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(otpCount) { index ->
                    val char = when {
                        index < otpText.length -> otpText[index].toString()
                        else -> placeholder
                    }
                    val isPlaceholder = index >= otpText.length

                    OtpCharBox(
                        char = char,
                        enabled = enabled,
                        isPlaceholder = isPlaceholder,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    )
                }
            }
        }
    )
}

@Composable
private fun OtpCharBox(
    char: String,
    enabled: Boolean,
    isPlaceholder: Boolean,
    modifier: Modifier = Modifier,
) {
    val textColor = when {
        !enabled -> MoaTheme.colors.coolGray60
        isPlaceholder -> MoaTheme.colors.coolGray60
        else -> MoaTheme.colors.black
    }

    Box(
        modifier = modifier
            .background(
                color = MoaTheme.colors.coolGray98,
                shape = RoundedCornerShape(12.dp)
            )
            .padding(vertical = 12.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = char,
            modifier = Modifier.defaultMinSize(minWidth = 34.dp, minHeight = 38.dp),
            color = textColor,
            style = MoaTheme.typography.display1Medium,
            textAlign = TextAlign.Center,
        )
    }
}


@Preview
@Composable
private fun Preview() {
    val text = remember { mutableStateOf("") }
    MaOtpTextField(
        otpText = text.value,
        onOtpTextChange = { value -> text.value = value },
        enabled = true
    )
}
