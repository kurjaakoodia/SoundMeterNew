package com.example.soundmeternew

import android.media.AudioRecord
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlin.math.log10
import kotlin.math.sqrt

class AudioViewModel(private val audio: Audio) : ViewModel() {
    private var isRecording = false
    private val audioRecord = audio.audioRecord

    private fun calculateRMS(buffer: ShortArray, readSize: Int): Double {
        var sum = 0.0
        for (i in 0 until readSize) {
            sum += buffer[i] * buffer[i] // Square each sample
        }
        return sqrt(sum / readSize) // Return the RMS
    }


    private fun startLoudnessLogging() {
        Thread {
            val buffer = ShortArray(audio.bufferSize)
            while (isRecording) {
                val readSize =
                    audioRecord?.read(buffer, 0, buffer.size, AudioRecord.READ_BLOCKING) ?: 0
                if (readSize > 0) {
                    val rms = calculateRMS(buffer, readSize)
                    val decibels = 20 * log10(rms)
                    Log.d("AudioLoudness", "Loudness: $decibels dB")
                } else {
                    Log.e("AudioRecord", "Failed to read data. Read size: $readSize")
                }
            }
        }.start()
    }

    // Start recording audio
    fun startRecording() {


        audioRecord?.startRecording()
        if (audioRecord?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
            Log.i("TAG", "didnt start")
        }

        isRecording = true

        startLoudnessLogging()
        Log.i("TAG", "startRecording: Started")
    }
     fun stopRecording() {
        audio.stopRecording()
        Log.i("TAG", "stopRecording:Happened ")
        isRecording = false
    }

}