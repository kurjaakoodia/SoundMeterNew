package com.example.soundmeternew

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.ByteBuffer
import com.example.soundmeternew.Recording

class Audio(private val context: Context) {

    companion object {
        const val RECORDER_SAMPLE_RATE = 11025
        const val AUDIO_SOURCE = MediaRecorder.AudioSource.DEFAULT
        const val CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO
        const val AUDIO_FORMAT = AudioFormat.ENCODING_PCM_16BIT
    }

    var audioRecord: AudioRecord? = null
    var bufferSize = AudioRecord.getMinBufferSize(
        RECORDER_SAMPLE_RATE,
        CHANNEL_CONFIG,
        AUDIO_FORMAT
    )
    private var isPaused = false
    private var outputFile: File? = null
    private var maxMeasurement = 0.0

    // Function to initialize the AudioRecord object
    fun initialize(outputDirectory: File): Boolean {
        audioRecord = getSupportedAudioRecord()
            ?: throw IllegalStateException("AudioRecord initialization failed.")
        outputFile = File(outputDirectory, "recording_${System.currentTimeMillis()}.pcm")

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


    fun saveAudio(buffer: ShortArray, readSize: Int) {
        if (outputFile == null || isPaused) return
        try {
            FileOutputStream(outputFile, true).use { outputStream ->
                val byteBuffer = ByteArray(readSize * 2)
                for (i in 0 until readSize) {
                    byteBuffer[i * 2] = (buffer[i].toInt() and 0xFF).toByte()
                    byteBuffer[i * 2 + 1] = (buffer[i].toInt() shr 8).toByte()
                }
                outputStream.write(byteBuffer)
            }
        } catch (e: IOException) {
            Log.e("Audio", "Error writing to file: ${e.message}")
        }
    }

    fun savePartialRecording() {
        if (audioRecord == null) {
            Log.e("Audio", "AudioRecord is null. Cannot save partial recording.")
            return
        }

        val buffer = ShortArray(bufferSize)
        val readSize = audioRecord?.read(buffer, 0, buffer.size) ?: 0
        if (readSize > 0) {
            val byteArray = ShortArrayToByteArray(buffer, readSize)
            try {
                val fos = FileOutputStream(outputFile, true) // Append to the file
                fos.write(byteArray)
                fos.close()
                Log.i("Audio", "Partial recording saved.")
            } catch (e: Exception) {
                Log.e("Audio", "Failed to save partial recording: ${e.message}")
            }
        }
    }

    private fun ShortArrayToByteArray(buffer: ShortArray, readSize: Int): ByteArray {
        val byteBuffer = ByteBuffer.allocate(readSize * 2) // 2 bytes per short
        for (i in 0 until readSize) {
            byteBuffer.putShort(buffer[i])
        }
        return byteBuffer.array()
    }

    fun getAudioDetails(): Recording?{
        val outputFile = getOutputFile()
        return if(outputFile != null && outputFile.exists()){
            getOutputFile()?.let {
                Recording(
                    filePath = it.absolutePath,
                    timestamp = System.currentTimeMillis(),
                    maxMeasurement = maxMeasurement
                )
            }
        }else {
            null
        }
    }

    // Pause recording
    fun pauseRecording() {
        isPaused = true
    }

    // Resume recording
    fun resumeRecording() {
        isPaused = false
    }

    // Stop recording audio
    fun stopRecording() {
        audioRecord?.stop()
    }

    // Release the AudioRecord object
    fun release() {
        audioRecord?.release()
    }

    fun setMax(max: Double){
        maxMeasurement = max
    }


    fun isRecordingPaused(): Boolean = isPaused

    fun getOutputFile(): File? = outputFile

}
