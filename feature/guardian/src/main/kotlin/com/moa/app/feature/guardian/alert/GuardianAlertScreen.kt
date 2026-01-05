package com.moa.app.feature.guardian.alert

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.moa.app.designsystem.component.product.topbar.MaTopAppBar

@Composable
fun GuardianAlertScreen(
    viewModel: GuardianAlertViewModel = hiltViewModel()
) {
    GuardianAlertContent(
        onBackClick = viewModel::navigateToBack
    )
}

@Composable
private fun GuardianAlertContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        MaTopAppBar(title = "알림", onBackClick = onBackClick)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewGuardianAlertContent() {
    GuardianAlertContent(
        onBackClick = {}
    )
}
