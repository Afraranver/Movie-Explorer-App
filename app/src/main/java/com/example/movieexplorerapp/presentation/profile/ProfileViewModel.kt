package com.example.movieexplorerapp.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorerapp.common.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private val _userName = mutableStateOf<String?>(null)
    val userName: State<String?> = _userName

    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    init {
        _userName.value = "John Doe"

        loadThemePreference()
    }

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
        saveThemePreference(_isDarkTheme.value)
    }

    private fun saveThemePreference(isDark: Boolean) {
        viewModelScope.launch {
            dataStoreRepository.saveThemePreference(isDark)
        }
    }

    private fun loadThemePreference() {
        viewModelScope.launch {
            _isDarkTheme.value = dataStoreRepository.getThemePreference()
        }
    }
}