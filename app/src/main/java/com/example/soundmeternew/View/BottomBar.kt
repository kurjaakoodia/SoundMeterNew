package com.example.soundmeternew.View

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class NavItemState(
    val title: String,
    val selectedIcon: androidx.compose.ui.graphics.vector.ImageVector,
    val unselectedIcon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainBottomBar() {
    // Define navigation items
    val items = listOf(
        NavItemState(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        NavItemState(
            title = "References",
            selectedIcon = Icons.Filled.Info,
            unselectedIcon = Icons.Outlined.Info
        )
    )

    // Remember the selected navigation state
    val bottomNavState = remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),
                containerColor = Color(0xFFE6F4CD) // Light green background
            ) {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = bottomNavState.value == index,
                        onClick = { bottomNavState.value = index },
                        icon = {
                            Icon(
                                imageVector = if (bottomNavState.value == index) item.selectedIcon else item.unselectedIcon,
                                contentDescription = item.title
                            )
                        },
                        label = {
                            Text(text = item.title)
                        }
                    )
                }
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                // Display content based on the selected tab
                Text(
                    text = when (bottomNavState.value) {
                        0 -> "Home Content"
                        1 -> "References Content"
                        else -> "Content Area"
                    },
                    modifier = Modifier.align(Alignment.Center),
                    color = Color.Gray
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainBottomBar() {
    MainBottomBar()
}
