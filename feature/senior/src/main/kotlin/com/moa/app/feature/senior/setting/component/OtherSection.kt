package com.moa.app.feature.senior.setting.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.R
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun OtherSection(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "기타",
            color = MoaTheme.colors.black,
            style = MoaTheme.typography.body1Bold,
            modifier = Modifier.padding(vertical = 10.dp)
        )

        OptionItem(
            icon = R.drawable.ic_headphones,
            title = "고객센터",
            onClick = {}
        )

        OptionItem(
            icon = R.drawable.ic_shield,
            title = "약관 및 정책",
            onClick = {}
        )

        OptionItem(
            icon = R.drawable.ic_logout,
            title = "로그아웃",
            onClick = {}
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    onClick = {},
                    indication = ripple(),
                    interactionSource = remember { MutableInteractionSource() },
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_stop),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MoaTheme.colors.red500),
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = "회원 탈퇴",
                color = MoaTheme.colors.red500,
                style = MoaTheme.typography.body1Medium,
                modifier = Modifier.padding(vertical = 10.dp)
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    OtherSection()
}
