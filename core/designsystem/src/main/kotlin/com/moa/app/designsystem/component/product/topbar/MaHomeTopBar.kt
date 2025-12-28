package com.moa.app.designsystem.component.product.topbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter.Companion.tint
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.moa.app.designsystem.R
import com.moa.app.designsystem.theme.MoaTheme

@Composable
fun MaHomeTopBar(
    onSettingClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(start = 20.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            imageVector = ImageVector.vectorResource(R.drawable.img_moa_logo),
            contentDescription = null,
            colorFilter = tint(MoaTheme.colors.coolGray60),
            modifier = Modifier.size(60.dp, 24.dp)
        )

        Image(
            imageVector = ImageVector.vectorResource(R.drawable.ic_setting),
            contentDescription = null,
            modifier = Modifier
                .clickable(
                    onClick = onSettingClick,
                    role = Role.Button,
                    interactionSource = null,
                    indication = null
                )
                .padding(12.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewMaHomeTopBar() {
    MaHomeTopBar(
        onSettingClick = {}
    )
}
