package com.example.soundmeternew

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

//import com.github.tehras.charts.line.LineChart
//import com.github.tehras.charts.line.renderer.yaxis.YAxisRenderer

@SuppressLint("DefaultLocale")
@Composable
fun Home(
    modifier: Modifier = Modifier,
    startRecording: ()-> Unit,
    stopRecording: ()-> Unit,
    resumeRecording: () -> Unit,
    pauseRecording: () -> Unit,
    audioViewModel: AudioViewModel
) {

    Box(modifier = Modifier.fillMaxSize()) {
        val decibelLevel by audioViewModel.dbMeasurement.collectAsState(initial = 0.0)
val maxMeasurement by audioViewModel.maxMeasurement.collectAsState(initial = 0.0)
        Column(
            modifier = modifier
                .fillMaxSize()
                .wrapContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Sound Meter",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF7CB342), // Green text
                modifier = Modifier.padding(top = 16.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "🔷 Round Button/false",
                fontSize = 16.sp,
                color = Color(0xFF673AB7), // Purple text
                modifier = Modifier.padding(8.dp)
            )

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .background(Color(0xFFD32F2F), shape = CircleShape) // Red circle button
            ) {
                Button(onClick = {
                    Log.d("RecordButton", "Recording started...")
                    startRecording()
                },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFD32F2F),    // Set the background color of the button
                        contentColor = Color.White      // Set the text color (content) of the button
                    )
                ) {
                    Text(
                        text = "Record",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Loudness: ${String.format("%.2f", decibelLevel)} dB",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Text(
                text = "Max: ${String.format("%.2f", maxMeasurement)} dB",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Button(onClick = {
                stopRecording()
                Log.d("StopButton", "Recording Stop.")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,    // Set the background color of the button
                    contentColor = Color.White      // Set the text color (content) of the button
                )
            ) {
                Text(
                    text = "Stop",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

            Button(onClick = {
                pauseRecording()
                Log.d("PauseButton", "Recording Paused.")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,    // Set the background color of the button
                    contentColor = Color.White      // Set the text color (content) of the button
                )
            ) {
                Text(
                    text = "Pause",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }

            Button(onClick = {
                resumeRecording()
                Log.d("ResumeButton", "Recording resumed.")
            },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Blue,    // Set the background color of the button
                    contentColor = Color.White      // Set the text color (content) of the button
                )
            ) {
                Text(
                    text = "resume",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                )
            }
            // Placeholder for the line chart
//            LineChart(
//                lines = listOf(
//                    listOf(2f, 5f, 4f, 7f, 8f, 6f, 3f, 2f, 9f), // Example data
//                    listOf(3f, 6f, 3f, 6f, 4f, 8f, 2f, 7f, 5f)
//                ),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp),
//                yAxisRenderer = YAxisRenderer.defaultRenderer()
//            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
 //  Home()
}