package com.example.movieexplorerapp.presentation.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.common.UserPreferences
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.presentation.auth.components.CustomTextField

@Composable
fun AuthScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var emailId by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var passwordVisible by remember { mutableStateOf(false) }

    val authState = viewModel.authState.value
    val context = LocalContext.current
    val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}".toRegex()

    // Handle auth state changes (loading, success, error)
    LaunchedEffect(authState) {
        when {
            authState.isLoading -> {
                // Show loading state
            }
            authState.isAuthenticated -> {
                Toast.makeText(context, "Logged In Successfully", Toast.LENGTH_SHORT).show()
                val userPreferences = UserPreferences(context)
                userPreferences.saveLoginState(true)
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.AuthScreen.route) { inclusive = true }
                }
            }
            authState.error != null -> {
                Toast.makeText(context, authState.error, Toast.LENGTH_LONG).show()
            }
        }
    }

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
            Image(
                painter = painterResource(id = R.drawable.app_logo_e),
                contentDescription = "App Logo",
                modifier = Modifier.size(180.dp),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit // Adjust the content scale as needed
            )

            // Welcome Text
            Text(
                text = "Movie Explorer App",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Text(
                text = "Login",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email TextField
            CustomTextField(
                value = emailId,
                onValueChange = {
                    emailId = it
                    // Reset email error when the value changes
                    emailError = false
                },
                label = "Email",
                placeholder = "Enter your email",
                isError = emailError
            )

            // Show email error message if the email is invalid
            if (emailError) {
                Text(
                    text = "Please enter a valid email",
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
                    // Check if the email is valid using regex
                    if (emailId.isEmpty() || !emailPattern.matches(emailId)) {
                        emailError = true
                    } else {
                        emailError = false
                        // Proceed with the login logic
                        viewModel.signIn(emailId, password)
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

            // Navigate to Signup Screen
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

            // Show loading state
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

