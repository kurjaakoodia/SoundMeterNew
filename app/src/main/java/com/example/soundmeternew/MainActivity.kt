package com.example.soundmeternew

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.example.soundmeternew.ui.theme.SoundMeterNewTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var audioRecorder: AudioRecorder
    private lateinit var recordingDao: DAO.RecordingDao
    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recordingDao = RecordingDatabase.AppDatabase.getDatabase(applicationContext).recordingDao()
        audioRecorder = AudioRecorder(applicationContext, recordingDao)


        setContent {
            SoundMeterNewTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android!")
                }
                @RequiresApi(Build.VERSION_CODES.S)
                fun startRecording() {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        audioRecorder.startRecording()
                    } // Start recording with a unique filename
                }

                suspend fun stopRecording() {
                    audioRecorder.stopRecording() // Stop recording and save to database
                }

                suspend fun resetRecording() {
                    audioRecorder.resetRecording() // Reset the recorder for reuse
                }

                fun onDestroy() {
                    super.onDestroy()
                    audioRecorder.release() // Release the recorder when done
                }
            }
            }
        }
    }

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SoundMeterNewTheme {
        Greeting("Android")
    }
}