package com.moa.app.feature.senior.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar
import com.moa.app.designsystem.theme.MoaTheme
import com.moa.app.feature.senior.setting.component.OtherSection
import com.moa.app.feature.senior.setting.component.ProfileSection

@Composable
fun SettingScreen() {
    SettingContent()
}

@Composable
private fun SettingContent() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        MaTopAppBar(
            title = "설정",
            onBackClick = {}
        )

        ProfileSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            name = "김대현",
            code = "0000",
            onCopyClick = {},
        )

        OtherSection(
            modifier = Modifier.padding(start = 20.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "앱 버전 정보",
                color = MoaTheme.colors.coolGray90,
                style = MoaTheme.typography.body1Medium,
            )
            Text(
                text = "v.1.0.0",
                color = MoaTheme.colors.coolGray90,
                style = MoaTheme.typography.body1Medium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun Preview() {
    SettingContent()
}
