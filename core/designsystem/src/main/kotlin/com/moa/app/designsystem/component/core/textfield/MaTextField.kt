package com.moa.app.designsystem.component.core.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaButtonDefaults
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun MaTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    isError: Boolean = false,
    readOnly: Boolean = false,
    textStyle: TextStyle = MoaTheme.typography.body1Medium,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = if (singleLine) 1 else Int.MAX_VALUE,
    minLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    placeholder: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    val borderColor = if (isError) MoaTheme.colors.red500 else Color.Transparent

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .background(
                color = MoaTheme.colors.coolGray98,
                shape = RoundedCornerShape(12.dp)
            )
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            ),
        enabled = enabled,
        readOnly = readOnly,
        textStyle = textStyle,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        maxLines = maxLines,
        minLines = minLines,
        visualTransformation = visualTransformation,
        onTextLayout = onTextLayout,
        interactionSource = interactionSource
    ) { innerTextField ->
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.CenterStart
            ) {
                if (value.isEmpty() && placeholder != null) {
                    placeholder()
                }
                innerTextField()
            }

            if (trailingContent != null) {
                trailingContent()
            }
        }
    }
}

@Preview
@Composable
private fun Preview() {
    var text by remember { mutableStateOf("") }

    MaTextField(
        value = text,
        onValueChange = { newValue -> text = newValue },
        isError = false,
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(
                text = "Placeholder",
                style = MoaTheme.typography.body1Medium,
                color = MoaTheme.colors.coolGray60
            )
        },
        trailingContent = {
            MaButton(
                onClick = {},
                colors = MaButtonDefaults.maButtonColors().copy(
                    defaultBackground = MoaTheme.colors.black,
                    pressedBackground = MoaTheme.colors.black,
                    disabledBackground = MoaTheme.colors.coolGray95,
                    defaultContentColor = MoaTheme.colors.white,
                    pressedContentColor = MoaTheme.colors.white,
                    disabledContentColor = MoaTheme.colors.white,
                )
            ) {
                Text(
                    text = "버튼",
                    style = MoaTheme.typography.body2Bold,
                    color = MoaTheme.colors.white,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                )
            }
        }
    )
}
