package com.moa.app.navigation

import android.content.Context
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.net.toUri
import timber.log.Timber

fun openUrlInBrowser(context: Context, url: String) {
    try {
        val customTabsIntent = CustomTabsIntent.Builder().build()
        customTabsIntent.launchUrl(context, url.toUri())
    } catch (e: Exception) {
        Timber.Forest.e(e, "Failed to open URL with CustomTabs: $url")
        try {
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        } catch (fallbackException: Exception) {
            Timber.Forest.e(fallbackException, "Failed to open URL with any browser: $url")
        }
    }
}
