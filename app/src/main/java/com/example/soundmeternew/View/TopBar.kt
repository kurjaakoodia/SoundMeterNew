package com.example.soundmeternew.View


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainBar() {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(10.dp)
                    .clip(RoundedCornerShape(15.dp)),
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon"
                        )
                    }
                },
                title = {
                    Text(
                        text = "Sound Meter",
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFE6F4CD) // Use a valid color representation
                )
            )
        },
        content = {}
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainBar() {
    MainBar()
}
