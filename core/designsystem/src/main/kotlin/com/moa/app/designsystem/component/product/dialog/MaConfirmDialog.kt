package com.moa.app.designsystem.component.product.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.theme.MoaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaConfirmDialog(
    title: String,
    modifier: Modifier = Modifier,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    onDialogDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(),
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDialogDismissRequest,
        properties = properties,
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(MoaTheme.colors.white)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = title,
                color = MoaTheme.colors.black,
                style = MoaTheme.typography.title1Bold,
                modifier = Modifier.padding(vertical = 20.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            MaButton(
                onClick = onConfirm,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
            ) {
                Text(
                    text = confirmButtonText,
                    style = MoaTheme.typography.body1Bold,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewMaConfirmDialog() {
    MaConfirmDialog(
        title = "회원탈퇴 하시겠습니까?",
        confirmButtonText = "탈퇴하기",
        onConfirm = {},
        onDialogDismissRequest = {},
    )
}
