package com.moa.app.designsystem.component.product.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.core.button.MaButton
import com.moa.app.designsystem.component.core.button.MaButtonDefaults
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun ProfileSection(
    modifier: Modifier = Modifier,
    name: String,
    code: String? = null,
    isCodeEnabled: Boolean = false,
    onCopyClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            text = "${name}님",
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.headLine2Bold,
        )

        if (isCodeEnabled) {
            Row(
                modifier = Modifier.height(height = 30.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
                Text(
                    text = "회원코드: $code",
                    color = MoaTheme.colors.coolGray60,
                    style = MoaTheme.typography.body1Medium,
                )

                MaButton(
                    onClick = onCopyClick,
                    colors = MaButtonDefaults.maButtonColors(
                        defaultBackground = MoaTheme.colors.coolGray70,
                        pressedBackground = MoaTheme.colors.coolGray70,
                        disabledBackground = MoaTheme.colors.coolGray70,
                        defaultContentColor = MoaTheme.colors.white,
                        pressedContentColor = MoaTheme.colors.white,
                        disabledContentColor = MoaTheme.colors.white,
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Text(
                        text = "복사",
                        style = MoaTheme.typography.body2Bold,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 12.dp),
                    )
                }
            }
        }
    }
}

@Preview()
@Composable
private fun Preview() {
    ProfileSection(
        name = "홍길동",
        code = "0000",
        onCopyClick = {},
    )
}

@Preview
@Composable
private fun PreviewEnabledCode() {
    ProfileSection(
        name = "홍길동",
        code = "0000",
        isCodeEnabled = true,
        onCopyClick = {},
    )
}
