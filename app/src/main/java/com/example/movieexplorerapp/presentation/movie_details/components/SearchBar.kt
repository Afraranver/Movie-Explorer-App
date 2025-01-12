package com.example.movieexplorerapp.presentation.movie_details.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    onCancel: () -> Unit,
    onSearch: (state: MutableState<TextFieldValue>) -> Unit
) {
    val state = remember { mutableStateOf(TextFieldValue()) }

    TextField(
        value = state.value,
        onValueChange = { value ->
            state.value = value
            onSearch(state)
        },
        placeholder = {
            Text(
                text = "Search here...",
                color = Color.Gray,
                style = TextStyle(fontSize = 16.sp)
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Gray,
                modifier = Modifier
                    .padding(12.dp)
                    .size(24.dp)
            )
        },
        trailingIcon = {
            if (state.value.text.isNotEmpty()) {
                IconButton(
                    onClick = {
                        state.value = TextFieldValue("")
                        onCancel()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Clear Icon",
                        tint = Color.Gray,
                        modifier = Modifier
                            .padding(12.dp)
                            .size(24.dp)
                    )
                }
            }
        },
        textStyle = TextStyle(color = Color.Black, fontSize = 18.sp),
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .shadow(5.dp, RoundedCornerShape(24.dp)) // Shadow for depth
            .background(Color.White, shape = RoundedCornerShape(24.dp)), // Background with rounded corners
        shape = RoundedCornerShape(24.dp), // Match shadow and background shape
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            containerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}