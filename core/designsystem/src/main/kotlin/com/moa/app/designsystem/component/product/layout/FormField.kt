package com.moa.app.designsystem.component.product.layout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.textfield.MaTextField
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun FormField(
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorMessage: String? = null,
    content: @Composable () -> Unit
) {
    val messageAlpha = if (isError && errorMessage != null) 1f else 0f

    Column(modifier = modifier) {
        content()

        Text(
            text = "* $errorMessage",
            color = MoaTheme.colors.red500.copy(alpha = messageAlpha),
            style = MoaTheme.typography.body2Medium,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}

@Preview
@Composable
private fun Preview() {
    var text by remember { mutableStateOf("") }
    val isError = text.isBlank()

    FormField(
        isError = isError,
        errorMessage = if (isError) "텍스트를 입력해주세요." else null,
        modifier = Modifier.fillMaxWidth()
    ) {
        MaTextField(
            value = text,
            onValueChange = { newValue -> text = newValue },
            modifier = Modifier.fillMaxWidth(),
            isError = isError,
            placeholder = {
                Text(
                    text = "Placeholder",
                    style = MoaTheme.typography.body1Medium,
                    color = MoaTheme.colors.coolGray60
                )
            },
        )
    }
}
