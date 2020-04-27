package com.example.portalgun.util

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
 * Launch a suspend [block] that posts a loading status
 */
fun ViewModel.launchWithStatus(loading: MutableLiveData<Boolean>, block: suspend () -> Unit) {
    viewModelScope.launch {
        try {
            loading.value = true
            block()
        } catch (e: Exception) {
            // TODO Add exception handler
        } finally {
            loading.value = false
        }
    }
}
