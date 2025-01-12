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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.movieexplorerapp.R
import com.example.movieexplorerapp.common.UserPreferences
import com.example.movieexplorerapp.presentation.Screen
import com.example.movieexplorerapp.presentation.auth.components.CustomTextField

@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = hiltViewModel()
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }

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
                Toast.makeText(context, "Signed Up Successfully", Toast.LENGTH_SHORT).show()
                val userPreferences = UserPreferences(context)
                userPreferences.saveLoginState(true)
                navController.navigate(Screen.Dashboard.route) {
                    popUpTo(Screen.SignUpScreen.route) { inclusive = true }
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
            // Signup Title
            Text(
                text = "Create an Account",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Email TextField
            CustomTextField(
                value = email,
                onValueChange = {
                    email = it
                    emailError = false
                },
                label = "Email",
                placeholder = "Enter your email",
                isError = emailError
            )
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

            // Confirm Password TextField
            CustomTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                    confirmPasswordError = false
                },
                label = "Confirm Password",
                placeholder = "Re-enter your password",
                isPassword = true,
                passwordVisible = confirmPasswordVisible,
                onPasswordVisibilityChange = { confirmPasswordVisible = !confirmPasswordVisible },
                isError = confirmPasswordError
            )
            if (confirmPasswordError) {
                Text(
                    text = "Password confirmation doesn't match",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Sign Up Button
            Button(
                onClick = {
                    var isValid = true

                    // Validate email
                    if (email.isEmpty() || !emailPattern.matches(email)) {
                        emailError = true
                        isValid = false
                    }

                    // Validate password
                    if (password.isEmpty()) {
                        passwordError = true
                        isValid = false
                    }

                    // Validate confirm password
                    if (confirmPassword.isEmpty() || password != confirmPassword) {
                        confirmPasswordError = true
                        isValid = false
                    }

                    if (isValid) {
                        viewModel.signUp(email, password)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(MaterialTheme.shapes.large),
                contentPadding = PaddingValues(vertical = 14.dp)
            ) {
                Text(
                    text = "Sign Up",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // Navigate back to Login Button
            OutlinedButton(
                onClick = {
                    navController.popBackStack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                contentPadding = PaddingValues(vertical = 14.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    text = "Already have an account? Login",
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

@Composable
fun LogoWithAppName() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo Image (Replace with your logo asset)
        Image(
            painter = painterResource(id = R.drawable.tmdb_logo_lg), // Replace with your logo resource
            contentDescription = "App Logo",
            modifier = Modifier
                .size(100.dp)
                .padding(bottom = 16.dp)
        )
        Text(
            text = "MyApp",
            style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    passwordVisible: Boolean,
    onPasswordVisibilityChanged: (Boolean) -> Unit
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val image = if (passwordVisible) {
                Icons.Default.Visibility
            } else {
                Icons.Default.VisibilityOff
            }

            IconButton(onClick = { onPasswordVisibilityChanged(!passwordVisible) }) {
                Icon(imageVector = image, contentDescription = "Toggle password visibility")
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}