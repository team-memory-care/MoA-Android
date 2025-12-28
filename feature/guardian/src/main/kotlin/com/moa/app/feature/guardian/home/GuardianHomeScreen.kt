package com.moa.app.feature.guardian.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.moa.app.designsystem.component.product.topbar.MaHomeTopBar

@Composable
fun GuardianHomeScreen(
    modifier: Modifier = Modifier,
) {

}

@Composable
private fun GuardianHomeContent(
    modifier: Modifier = Modifier,
    onSettingClick: () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MaHomeTopBar(onSettingClick = onSettingClick)
    }
}

@Preview
@Composable
private fun Preview() {
    GuardianHomeContent(
        onSettingClick = {}
    )
}
