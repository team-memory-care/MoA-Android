package com.moa.app.feature.report

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.moa.app.navigation.AppRoute

fun NavGraphBuilder.reportGraph() {
    composable<AppRoute.Report> { ReportScreen() }
}
