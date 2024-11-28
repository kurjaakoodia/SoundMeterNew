package com.example.soundmeternew

//import android.os.Build
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.BorderStroke
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Surface
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.lifecycleScope
//import com.example.soundmeternew.ui.theme.SoundMeterNewTheme
//import kotlinx.coroutines.launch
//
//class MainActivity : ComponentActivity() {
//
//    // Added this useless comment to push a branch change.
//
//    private lateinit var audioRecorder: AudioRecorder
//    private lateinit var recordingDao: DAO.RecordingDao
//
//    @RequiresApi(Build.VERSION_CODES.S)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        recordingDao = RecordingDatabase.AppDatabase.getDatabase(applicationContext).recordingDao()
//        audioRecorder = AudioRecorder(applicationContext, recordingDao)
//
//        setContent {
//            SoundMeterNewTheme {
//                Surface(
//                    modifier = Modifier.fillMaxSize(),
//                    color = MaterialTheme.colorScheme.background
//                ) {
//
//                }
//
//
//            }
//        }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.S)
//    private fun startRecording() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//            audioRecorder.startRecording()
//        }
//    }
//
//    private fun stopRecording() {
//        lifecycleScope.launch {
//            audioRecorder.stopRecording() // Stop recording and save to database
//        }
//    }
//
//    private fun resetRecording() {
//        lifecycleScope.launch {
//            audioRecorder.resetRecording() // Reset the recorder for reuse
//        }
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        audioRecorder.release() // Release the recorder when done
//    }
//}
//
//
//
//
//@Composable
//fun TitleText(titlename: String, modifier: Modifier = Modifier) {
//    val brush = Brush.linearGradient(
//        colors = listOf(
//            Color(0xFF42A5F5),
//            Color(0xFF9E82F0)
//        )
//    )
//
//    Box(
//        modifier = modifier
//            .padding(top = 50.dp)
//            .background(brush, shape = RoundedCornerShape(10.dp))
//            .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust padding for space around text
//    ) {
//        Text(
//            text = titlename,
//            fontWeight = FontWeight.Bold,
//            color = Color.White, // Set color as needed to contrast with background
//            textAlign = TextAlign.Center
//        )
//    }
//}
//
//
//@Composable
//fun RecordButton(modifier: Modifier = Modifier) {
//    val brush = Brush.linearGradient(
//        colors = listOf(
//            Color(0xFF9E82F0),
//            Color(0xFF42A5F5)
//        )
//    )
//
//    Button(
//        onClick = { /* TODO */ },
//        modifier = modifier
//           .background(brush, shape = RoundedCornerShape(150.dp)),
//        //border = BorderStroke(1.dp, Color.White),
//        shape = RoundedCornerShape(60.dp),
//        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
//    ) {
//        Text("Record",
//        color = Color.White,
//        )
//
//    }
//}
//
//
//
//
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SoundMeterNewTheme {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            TitleText(titlename = "Sound Meter")
//            Spacer(modifier = Modifier.height(200.dp))
//            RecordButton()
//        }
//    }
//}

import android.graphics.Color.rgb
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.ui.graphics.Brush
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

        enableEdgeToEdge()
        setContent {
            SoundMeterNewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyBottomAppBar()
                }
            }
        }
    }

    private fun startRecording() {
        try {
            audio.initialize()
            audio.startRecording()
            Log.i("TAG", audio.getMetrics())
            isRecording = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

    private fun stopRecording() {
        audio.stopRecording()
        Log.i("TAG", "stopRecording:Happened ")
        isRecording = false
    }

    private fun requestAudioPermission() {
        val permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
                if (isGranted) {
                    startRecording()
                } else {
                    // Handle permission denial
                    Toast.makeText(
                        this,
                        "Microphone permission is required to record audio.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        permissionLauncher.launch(android.Manifest.permission.RECORD_AUDIO)
    }

    override fun onDestroy() {
        super.onDestroy()
        audio.release()
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomAppBar() {
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
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
                Home()
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
        MyBottomAppBar()
    }
}