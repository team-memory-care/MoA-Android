package com.moa.app.ui.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController

/**
 * [NavBackStackEntry]에서 부모 [NavGraph]의 [ViewModel]을 가져오기 위한 확장 함수입니다.
 * 만약 부모 [NavGraph]가 없을 경우, 현재 [NavBackStackEntry]를 기준으로 [ViewModel]을 생성합니다.
 */
@Composable
inline fun <reified T : ViewModel> NavBackStackEntry.sharedViewModel(navController: NavController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) { navController.getBackStackEntry(navGraphRoute) }
    return hiltViewModel(parentEntry)
}
