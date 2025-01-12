package com.example.movieexplorerapp.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.presentation.auth.AuthViewModel
import com.example.movieexplorerapp.presentation.profile.components.LogOffConfirmationDialog
import com.example.movieexplorerapp.presentation.profile.components.SettingsSection

@Composable
fun ProfileScreen(
    navController: NavHostController,
    viewModel: ProfileViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val userName = viewModel.userName.value
    val isDarkTheme = viewModel.isDarkTheme.value
    val context = LocalContext.current

    // State for dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
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
                Text(
                    text = userName ?: "User Name",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    ),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Divider(
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            // Middle Section: Settings
            SettingsSection(
                isDarkTheme = isDarkTheme,
                onToggleTheme = { viewModel.toggleTheme() }
            )

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

                    // Navigate to the Auth screen and clear all screens in the back stack
                    navController.navigate(Screen.AuthScreen.route) {
                        // Pop up everything from the stack
                        popUpTo(navController.graph.startDestinationId) { inclusive = true }

                        // Ensure the user cannot navigate back to any previous screen
                        launchSingleTop = true
                        restoreState = false
                    }
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

