package com.example.movieexplorerapp.presentation.profile

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.NavController
import com.example.movieexplorerapp.presentation.auth.AuthViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: NavController
    private lateinit var authViewModel: AuthViewModel
    private lateinit var profileViewModel: ProfileViewModel

    @Before
    fun setup() {
//        navController = mock()
//        authViewModel = mock()
//        profileViewModel = mock()

        // Mocking necessary values
//        whenever(profileViewModel.userName).thenReturn(mutableStateOf("Test User"))
//        whenever(profileViewModel.userEmail).thenReturn(mutableStateOf("testuser@example.com"))
//        whenever(profileViewModel.userPhotoUrl).thenReturn(mutableStateOf("https://example.com/photo.jpg"))
//        whenever(profileViewModel.isDarkTheme).thenReturn(mutableStateOf(false))

        // Set up the composable
        composeTestRule.setContent {
            ProfileScreen(
                navController = navController,
                viewModel = profileViewModel,
                authViewModel = authViewModel
            )
        }
    }

    @Test
    fun testLogOffButtonClick() {
        // Find the 'Log Off' button and click it
        composeTestRule.onNodeWithText("Log Off")
            .performClick()

        // Verify that the LogOffConfirmationDialog is shown
        composeTestRule.onNodeWithText("Are you sure you want to log off?")
            .assertIsDisplayed()

        // Click the confirm button in the dialog
        composeTestRule.onNodeWithText("Confirm")
            .performClick()

        // Verify that the signOut function in AuthViewModel is called
//        verify(authViewModel).signOut()

        // Verify that the user preferences are cleared
//        val context = composeTestRule.activity.applicationContext
//        val userPreferences = UserPreferences(context)
//        verify(userPreferences).clearLoginState()

        // Verify the navigation to the AuthScreen happens
//        verify(navController).navigate(Screen.AuthScreen.route) {
//            popUpTo(0) { inclusive = true }
//            launchSingleTop = true
//            restoreState = false
//        }
    }

    @Test
    fun testLogOffDialogDismiss() {
        // Find the 'Log Off' button and click it
        composeTestRule.onNodeWithText("Log Off")
            .performClick()

        // Verify the confirmation dialog is displayed
        composeTestRule.onNodeWithText("Are you sure you want to log off?")
            .assertIsDisplayed()

        // Click the dismiss button in the dialog
        composeTestRule.onNodeWithText("Dismiss")
            .performClick()

        // Verify that the dialog is dismissed (no longer visible)
        composeTestRule.onNodeWithText("Are you sure you want to log off?")
            .assertDoesNotExist()
    }
}