package com.example.movieexplorerapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.presentation.auth.components.CustomTextField

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    val authState = viewModel.authState.value
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Logo
            Icon(
                painter = painterResource(id = R.drawable.tmdb_logo_lg),
                contentDescription = "TMDb Logo",
                modifier = Modifier.size(180.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Username TextField
            CustomTextField(
                value = username,
                onValueChange = {
                    username = it
                    usernameError = false
                },
                label = "Username",
                placeholder = "Enter your username",
                isError = usernameError
            )
            if (usernameError) {
                Text(
                    text = "Username cannot be empty",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Password TextField
            CustomTextField(
                value = password,
                onValueChange = {
                    password = it
                    passwordError = false
                },
                label = "Password",
                placeholder = "Enter your password",
                isPassword = true,
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                isError = passwordError
            )
            if (passwordError) {
                Text(
                    text = "Password cannot be empty",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Login Button
            Button(
                onClick = {
                    // Validation
                    var isValid = true
                    if (username.isEmpty()) {
                        usernameError = true
                        isValid = false
                    }
                    if (password.isEmpty()) {
                        passwordError = true
                        isValid = false
                    }

                    if (isValid) {
                        viewModel.signIn(username, password)

                        if (authState.isAuthenticated) {
                            Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                            navController.navigate("dashboard_screen")
                        } else {
                            val errorMessage = authState.error ?: "An unknown error occurred. Please try again."
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(MaterialTheme.shapes.large),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(
                    text = "Login",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Navigate to Signup Button
            OutlinedButton(
                onClick = {
                    navController.navigate(Screen.SignUpScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                contentPadding = PaddingValues(vertical = 14.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Don't have an account? Sign Up",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Loading Indicator
            if (authState.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

