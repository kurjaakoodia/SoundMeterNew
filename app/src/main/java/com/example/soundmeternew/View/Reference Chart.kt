package com.example.soundmeternew.View

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CurrentVolumeText(currentVolume: String, modifier: Modifier = Modifier) {
    Text(
        text = "Current Volume: $currentVolume",
        fontWeight = FontWeight.Bold,
        color = Color.Black,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
    )
}

@Composable
fun ReferenceChart(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            //.background(Color.LightGray, shape = RoundedCornerShape(8.dp)) // grey
            //.background(Color.White, shape = RoundedCornerShape(8.dp)) // white
            //.background(Color(0xFFF0F0F0), shape = RoundedCornerShape(8.dp)) // whiter grey
            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp)) // middle grey
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reference Chart",
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        val colors = listOf(
            Triple(Color(0xFFD32F2F), "140 dB", "Gunshot"),
            Triple(Color(0xFFFF7043), "130 dB", "Jackhammer"),
            Triple(Color(0xFFFFEB3B), "120 dB", "Ambulance Siren"),
            Triple(Color(0xFF66BB6A), "110 dB", "Concert"),
            Triple(Color(0xFF42A5F5), "100 dB", "Motorcycle"),
            Triple(Color(0xFF9C27B0), "90 dB", "Subway"),
            Triple(Color(0xFF673AB7), "80 dB", "Alarm Clock"),
            Triple(Color(0xFF3F51B5), "70 dB", "Vacuum Cleaner"),
            Triple(Color(0xFF2196F3), "60 dB", "Normal Conversation"),
            Triple(Color(0xFF00BCD4), "50 dB", "Refrigerator"),
            Triple(Color(0xFF009688), "40 dB", "Library"),
            Triple(Color(0xFF4CAF50), "30 dB", "Whisper"),
            Triple(Color(0xFF8BC34A), "20 dB", "Clock Ticking"),
            Triple(Color(0xFFCDDC39), "10 dB", "Breathing")
        )

        colors.forEach { (color, label, description) ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp, 20.dp)
                        .background(color, shape = RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = label,
                    color = Color.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = description,
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CurrentVolumeText(currentVolume = "0 dB")
        Spacer(modifier = Modifier.height(20.dp))
        ReferenceChart()
    }
}