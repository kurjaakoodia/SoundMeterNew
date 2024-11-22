package com.example.soundmeternew

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.soundmeternew.ui.theme.Red40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Search() {
    var query by remember { mutableStateOf("") }
    var isSearchActive by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF7F7F7)), // Light background for contrast
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Search Bar at the top
        SearchBar(
            query = query,
            onQueryChange = { query = it },
            onSearch = {
                // Action to perform on search (e.g., filter list)
                isSearchActive = true
            },
            active = isSearchActive,
            onActiveChange = { isSearchActive = it },
            placeholder = { Text("Search here...") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = Color.Gray
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { query = "" }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear Search",
                            tint = Color.Gray
                        )
                    }
                }
            },
            colors = SearchBarDefaults.colors(
                containerColor = Color.White,
                dividerColor = Color.LightGray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .height(56.dp)
        ) {
            // Content inside the dropdown or suggestions
            Text(
                text = "Suggestions coming soon...",
                modifier = Modifier.padding(16.dp),
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Main content area
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF0F0F0)), // Subtle gray background
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (query.isEmpty()) "Search" else "Searching for \"$query\"...",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Red40
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchPreview() {
    Search()
}