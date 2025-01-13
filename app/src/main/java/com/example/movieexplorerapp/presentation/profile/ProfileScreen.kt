package com.example.movieexplorerapp.presentation.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.movieexplorerapp.common.UserPreferences
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.presentation.auth.AuthViewModel
import com.example.movieexplorerapp.presentation.dashboard.components.BottomNavigationBar
import com.example.movieexplorerapp.presentation.profile.components.LogOffConfirmationDialog

@Composable
fun ProfileScreen(
    navController: NavController,
    viewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userName = viewModel.userName.value
    val userEmail = viewModel.userEmail.value
    val userPhotoUrl = viewModel.userPhotoUrl.value
    val isDarkTheme = viewModel.isDarkTheme.value
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top Section: Profile Info
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Image
                userPhotoUrl?.let {
                    AsyncImage(
                        model = it,
                        contentDescription = "Profile Image",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                        contentScale = ContentScale.Crop
                    )
                } ?: run {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Default Profile",
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surface),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = userName.ifEmpty { "User Name" },
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    ),
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = userEmail.ifEmpty { "Email not available" },
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 16.sp
                    ),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            // Middle Section: Settings
            // Add any settings you want to display, such as dark theme toggle, etc.
            // SettingsSection(
            //    isDarkTheme = isDarkTheme,
            //    onToggleTheme = { viewModel.toggleTheme() }
            // )

            // Bottom Section: Logoff Button
            Button(
                onClick = { showDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // Add padding above the bottom navigation
            ) {
                Text("Log Off")
            }
        }

        // Show the confirmation dialog
        if (showDialog) {
            LogOffConfirmationDialog(
                onConfirm = {
                    // Log off the user and clear the navigation stack
                    authViewModel.signOut()

                    val userPreferences = UserPreferences(context)
                    userPreferences.clearLoginState()

                    // Navigate to the Auth screen and clear all screens in the back stack
                    navController.navigate(Screen.AuthScreen.route) {
                        // Pop everything up to the start destination and include it
                        popUpTo(0) { inclusive = true }

                        // Avoid recreating the stack by ensuring a single top instance
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

