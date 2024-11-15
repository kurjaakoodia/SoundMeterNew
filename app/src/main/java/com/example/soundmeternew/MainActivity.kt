package com.example.soundmeternew

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.soundmeternew.ui.theme.SoundMeterNewTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var audioRecorder: AudioRecorder
    private lateinit var recordingDao: DAO.RecordingDao

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recordingDao = RecordingDatabase.AppDatabase.getDatabase(applicationContext).recordingDao()
        audioRecorder = AudioRecorder(applicationContext, recordingDao)

        setContent {
            SoundMeterNewTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }


            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            audioRecorder.startRecording()
        }
    }

    private fun stopRecording() {
        lifecycleScope.launch {
            audioRecorder.stopRecording() // Stop recording and save to database
        }
    }

    private fun resetRecording() {
        lifecycleScope.launch {
            audioRecorder.resetRecording() // Reset the recorder for reuse
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        audioRecorder.release() // Release the recorder when done
    }
}




@Composable
fun TitleText(titlename: String, modifier: Modifier = Modifier) {
    val brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF42A5F5),
            Color(0xFF9E82F0)
        )
    )

    Box(
        modifier = modifier
            .padding(top = 50.dp)
            .background(brush, shape = RoundedCornerShape(10.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust padding for space around text
    ) {
        Text(
            text = titlename,
            fontWeight = FontWeight.Bold,
            color = Color.White, // Set color as needed to contrast with background
            textAlign = TextAlign.Center
        )
    }
}


@Composable
fun RecordButton(modifier: Modifier = Modifier) {
    val brush = Brush.linearGradient(
        colors = listOf(
            Color(0xFF9E82F0),
            Color(0xFF42A5F5)
        )
    )

    Button(
        onClick = { /* TODO */ },
        modifier = modifier
           .background(brush, shape = RoundedCornerShape(150.dp)),
        //border = BorderStroke(1.dp, Color.White),
        shape = RoundedCornerShape(60.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
    ) {
        Text("Record",
        color = Color.White,
        )

    }
}





@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SoundMeterNewTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleText(titlename = "Sound Meter")
            Spacer(modifier = Modifier.height(200.dp))
            RecordButton()
        }
    }
}

