package com.moa.app.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun TerminateOnDoubleBack(
    enabled: Boolean = true,
    delayMillis: Long = 2000L,
    message: String = "뒤로가기를 한 번 더 누르면 종료됩니다.",
) {
    val activity = LocalActivity.current
    var backPressedOnce by remember { mutableStateOf(false) }
    val context = LocalContext.current

    LaunchedEffect(backPressedOnce) {
        if (backPressedOnce) {
            delay(delayMillis)
            backPressedOnce = false
        }
    }

    BackHandler(enabled = enabled) {
        if (backPressedOnce) {
            activity?.finish()
        } else {
            backPressedOnce = true
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }
}
