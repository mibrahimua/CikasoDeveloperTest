package com.mibrahimuadev.cikasodevelopertest.ui.barang

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class StokViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StokViewModel::class.java)) {
            return StokViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}