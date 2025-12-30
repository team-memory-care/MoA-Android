package com.moa.app.main

import androidx.lifecycle.ViewModel
import com.moa.app.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class
MainViewModel @Inject constructor(
    private val navigator: Navigator
) : ViewModel() {

    val navigationEvents = navigator.events
}
