package com.example.soundmeternew

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import kotlin.math.log10
import kotlin.math.sqrt

class Audio(private val context: Context) {

    companion object {
        const val RECORDER_SAMPLE_RATE = 11025
        const val AUDIO_SOURCE = MediaRecorder.AudioSource.DEFAULT
        const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    }

    private var audioRecord: AudioRecord? = null
    private var bufferSize = AudioRecord.getMinBufferSize(
        RECORDER_SAMPLE_RATE,
        CHANNEL_CONFIG,
        AUDIO_FORMAT
    )


    private var isRecording = false


    // Function to initialize the AudioRecord object
    fun initialize(): Boolean {
        audioRecord = getSupportedAudioRecord()
            ?: throw IllegalStateException("AudioRecord initialization failed.")
        return true
    }


    private fun getSupportedAudioRecord(): AudioRecord? {
        val configurations = listOf(
            Triple(44100, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT),
            Triple(22050, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT),
            Triple(16000, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT),
            Triple(11025, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)
        )

        for ((sampleRate, channelConfig, audioFormat) in configurations) {
            val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
            Log.i("getSupportedAudioRecord", bufferSize.toString())
            if (bufferSize > 0) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        android.Manifest.permission.RECORD_AUDIO
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    throw SecurityException("RECORD_AUDIO permission not granted.")
                }
                // Initialize the AudioRecord object
                val audioRecord = AudioRecord(
                    AUDIO_SOURCE,
                    RECORDER_SAMPLE_RATE,
                    CHANNEL_CONFIG,
                    AUDIO_FORMAT,
                    bufferSize
                )

                if (audioRecord.state == AudioRecord.STATE_INITIALIZED) {
                    return audioRecord
                } else {
                    audioRecord.release()
                }
            }
        }
        return null // No valid configuration found
    }



    // Start recording audio
    fun startRecording() {


        audioRecord?.startRecording()
        isRecording = true

        startLoudnessLogging()
        Log.i("TAG", "startRecording: Started")
    }
    private fun startLoudnessLogging() {
        Thread {
            val buffer = ShortArray(bufferSize)
            while (isRecording) {
                val readSize = audioRecord?.read(buffer, 0, buffer.size, AudioRecord.READ_BLOCKING) ?: 0
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

    private fun calculateRMS(buffer: ShortArray, readSize: Int): Double {
        var sum = 0.0
        for (i in 0 until readSize) {
            sum += buffer[i] * buffer[i] // Square each sample
        }
        return sqrt(sum / readSize) // Return the RMS
    }


    fun getMetrics(): String{
        return audioRecord?.sampleRate.toString()
    }

    // Stop recording audio
    fun stopRecording() {
        audioRecord?.stop()
        isRecording = false

    }

    // Release the AudioRecord object
    fun release() {
        audioRecord?.release()
    }
}
