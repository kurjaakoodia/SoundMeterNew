package com.example.soundmeternew

import android.graphics.Color.rgb
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.soundmeternew.ui.theme.SoundMeterNewTheme
import com.example.soundmeternew.ui.theme.Red40

class MainActivity : ComponentActivity() {

    private lateinit var audio: Audio
    private var isRecording = false

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the Audio class
        audio = Audio(this)
audio.initialize()
        enableEdgeToEdge()
        setContent {
            SoundMeterNewTheme {
                val audioViewModel = AudioViewModel(audio)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyBottomAppBar(audioViewModel)
                }
            }
        }
    }
//    private fun stopRecording() {
//        audio.stopRecording()
//        Log.i("TAG", "stopRecording:Happened ")
//        isRecording = false
//    }
    override fun onDestroy() {
        super.onDestroy()
        audio.release()
    }

                }



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomAppBar(audioViewModel: AudioViewModel) {
    val navigationController = rememberNavController()
//    val context = LocalContext.current.applicationContext
    val selected = remember { mutableStateOf(Icons.Default.Home) }


    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu Icon"
                        )
                    }
                },
                title = {
                    Text("Sound Meter")
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(rgb(154, 9, 14)),

                    )
            )
        },

        bottomBar = {
            BottomAppBar(
                containerColor = Red40
            ) {
                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Home
                        navigationController.navigate(Screens.Home.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Home,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (selected.value == Icons.Default.Home) Color.White else Color.Gray
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Search
                        navigationController.navigate(Screens.Search.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (selected.value == Icons.Default.Search) Color.White else Color.Gray
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Info
                        navigationController.navigate(Screens.Dictionary.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Info,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (selected.value == Icons.Default.Info) Color.White else Color.Gray
                    )
                }

                IconButton(
                    onClick = {
                        selected.value = Icons.Default.Menu
                        navigationController.navigate(Screens.Records.screen) {
                            popUpTo(0)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        Icons.Default.Menu,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = if (selected.value == Icons.Default.Menu) Color.White else Color.Gray
                    )
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = Screens.Home.screen,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Screens.Home.screen) {
                Home(startRecording = { audioViewModel.startRecording() }, stopRecording = {audioViewModel.stopRecording()})
            }
            composable(Screens.Search.screen) {
                Search()
            }
            composable(Screens.Dictionary.screen) {
                Dictionary()
            }
            composable(Screens.Records.screen) {
                Records()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SoundMeterNewTheme {
//        MyBottomAppBar()
    }
}

