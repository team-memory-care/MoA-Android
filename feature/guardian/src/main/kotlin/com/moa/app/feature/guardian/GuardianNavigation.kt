package com.moa.app.feature.guardian

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moa.app.feature.guardian.alert.GuardianAlertScreen
import com.moa.app.feature.guardian.home.GuardianHomeScreen
import com.moa.app.feature.guardian.setting.GuardianSettingScreen
import com.moa.app.navigation.AppRoute

fun NavGraphBuilder.guardianGraph() {
    composable<AppRoute.GuardianHome> { GuardianHomeScreen() }
    composable<AppRoute.GuardianSetting> { GuardianSettingScreen() }
    composable<AppRoute.GuardianAlert> { GuardianAlertScreen() }
}
