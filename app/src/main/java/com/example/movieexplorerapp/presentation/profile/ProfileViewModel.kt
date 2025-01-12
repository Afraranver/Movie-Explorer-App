package com.example.movieexplorerapp.presentation.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieexplorerapp.common.DataStoreRepository
import com.example.movieexplorerapp.data.repository.FirebaseAuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository,
    private val firebaseAuthRepository: FirebaseAuthRepositoryImpl
) : ViewModel() {

    private val _isDarkTheme = mutableStateOf(false)
    val isDarkTheme: State<Boolean> = _isDarkTheme

    private val _userName = mutableStateOf("")
    val userName: State<String> = _userName

    private val _userEmail = mutableStateOf("")
    val userEmail: State<String> = _userEmail

    private val _userPhotoUrl = mutableStateOf<String?>(null)
    val userPhotoUrl: State<String?> = _userPhotoUrl

    init {
        getUserInfo()
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

    private fun getUserInfo() {
        viewModelScope.launch {
            val userInfo = firebaseAuthRepository.getUserInfo()
            userInfo?.let {
                _userName.value = it.name
                _userEmail.value = it.email
                _userPhotoUrl.value = it.photoUrl
            }
        }
    }
}