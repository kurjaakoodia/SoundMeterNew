package com.example.soundmeternew

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.io.File
import java.io.IOException

class AudioRecorder(
    private val context: Context,
    private val recordingDao: RecordingDao
) {



    private var mediaRecorder: MediaRecorder? = null
    private var audioFile: File? = null
    private var isRecording = false
    private var startTime: Long = 0

    @RequiresApi(Build.VERSION_CODES.S)
    fun startRecording(filePath: String? = null) {
        val fileName = filePath ?: "recorded_audio_${System.currentTimeMillis()}.wav"
        audioFile = File(context.filesDir, fileName)


        mediaRecorder = MediaRecorder(context).apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(audioFile?.absolutePath)

            try {
                prepare()
                start()
                isRecording = true
                startTime = System.currentTimeMillis()
            } catch (e: IOException) {
                Log.e("AudioRecorder", "Failed to prepare/start MediaRecorder", e)
                release() // Release in case of failure
            }
        }
    }

    suspend fun stopRecording() {
        if (isRecording) {
            try {
                mediaRecorder?.apply {
                    stop()
                    release()
                }
                isRecording = false

                // Calculate actual duration
                val duration = System.currentTimeMillis() - startTime

                // Save file path and metadata to the Room DB
//                val recording = Recording(
//                    filePath = audioFile?.absolutePath ?: "",
//                    timestamp = startTime,
//                    duration = duration
//                )
//                recordingDao.insertRecording(recording)
            } catch (e: IllegalStateException) {
                Log.e("AudioRecorder", "Failed to stop MediaRecorder", e)
            } finally {
                mediaRecorder = null
            }
        }
    }

    suspend fun resetRecording() {
        if (isRecording) {
            stopRecording()
        }
        mediaRecorder?.reset() // Reset MediaRecorder to reuse
    }

    fun release() {
        mediaRecorder?.release()
        mediaRecorder = null
    }
}
