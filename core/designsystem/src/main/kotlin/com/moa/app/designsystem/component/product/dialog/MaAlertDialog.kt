package com.moa.app.designsystem.component.product.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaButtonColors
import com.moa.app.designsystem.theme.MoaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaAlertDialog(
    title: String,
    modifier: Modifier = Modifier,
    content: String? = null,
    textAlign: TextAlign = TextAlign.Center,
    confirmButtonText: String,
    dismissButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    onDialogDismissRequest: (() -> Unit)? = null,
    properties: DialogProperties = DialogProperties(),
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDialogDismissRequest ?: onDismiss,
        properties = properties,
    ) {
        Column(
            modifier = Modifier
                .background(
                    color = MoaTheme.colors.white,
                    shape = RoundedCornerShape(12.dp),
                )
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.padding(vertical = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = MoaTheme.colors.black,
                    style = MoaTheme.typography.title1Bold,
                    textAlign = textAlign,
                    modifier = Modifier.fillMaxWidth()
                )

                if (content != null) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = content,
                        color = MoaTheme.colors.coolGray60,
                        style = MoaTheme.typography.body2Medium,
                        textAlign = textAlign,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.height(56.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MaButton(
                    onClick = onDismiss,
                    colors = MaButtonColors(
                        defaultBackground = MoaTheme.colors.coolGray98,
                        pressedBackground = MoaTheme.colors.coolGray96,
                        disabledBackground = MoaTheme.colors.coolGray98,
                        defaultContentColor = MoaTheme.colors.coolGray50,
                        pressedContentColor = MoaTheme.colors.coolGray50,
                        disabledContentColor = MoaTheme.colors.coolGray50
                    ),
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                ) {
                    Text(
                        text = dismissButtonText,
                        style = MoaTheme.typography.body1Bold,
                    )
                }

                MaButton(
                    onClick = onConfirm,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                ) {
                    Text(
                        text = confirmButtonText,
                        style = MoaTheme.typography.body1Bold,
                    )
                }
            }
        }
    }
}

@Preview(name = "content - x")
@Composable
private fun Preview() {
    MaAlertDialog(
        title = "회원탈퇴 하시겠습니까?",
        confirmButtonText = "탈퇴하기",
        dismissButtonText = "취소",
        onConfirm = {},
        onDismiss = {},
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    )
}

@Preview(name = "content - o")
@Composable
private fun Preview2() {
    MaAlertDialog(
        title = "퀴즈를 그만두시나요?",
        content = "그만두면 지금까지\n푼 퀴즈는 저장되지 않아요.",
        confirmButtonText = "탈퇴하기",
        dismissButtonText = "취소",
        onConfirm = {},
        onDismiss = {},
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        ),
    )
}
