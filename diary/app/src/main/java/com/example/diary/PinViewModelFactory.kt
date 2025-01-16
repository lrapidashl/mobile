package com.example.diary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PinViewModelFactory(private val settingStorage: SettingStorage) : ViewModelProvider.Factory {
	override fun <T : ViewModel> create(modelClass: Class<T>): T {
		if (modelClass.isAssignableFrom(PinViewModel::class.java)) {
			@Suppress("UNCHECKED_CAST")
			return PinViewModel(settingStorage) as T
		}
		throw IllegalArgumentException("Unknown ViewModel class")
	}
}