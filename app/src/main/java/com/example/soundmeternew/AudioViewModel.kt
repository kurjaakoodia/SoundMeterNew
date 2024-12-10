package com.example.soundmeternew

import android.media.AudioRecord
import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.math.log10
import kotlin.math.sqrt

class AudioViewModel(private val audio: Audio,
//                     private val recordingDao: RecordingDao
)
    : ViewModel() {
    private var isRecording = false
    private val audioRecord = audio.audioRecord
    private var _dbMeasurement = MutableStateFlow(0.0)
    var dbMeasurement = _dbMeasurement.asStateFlow()
    private var max = 0.0
    private var _maxMeasurement = MutableStateFlow(0.0)
    var maxMeasurement = _maxMeasurement.asStateFlow()

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
                if (!audio.isRecordingPaused()) {
                    val readSize =
                        audioRecord?.read(buffer, 0, buffer.size, AudioRecord.READ_BLOCKING) ?: 0
                    if (readSize > 0) {
                        audio.saveAudio(buffer, readSize)
                        val rms = calculateRMS(buffer, readSize)
                        val decibels = 20 * log10(rms)
                        if (decibels > max) {
                            max = decibels
                            audio.setMax(max)
                            _maxMeasurement.update { decibels.coerceAtLeast(0.0) }

                        }
                        _dbMeasurement.update { decibels.coerceAtLeast(0.0) }
                        Log.d("AudioLoudness", "Loudness: $decibels dB")
                    } else {
                        Log.e("AudioRecord", "Failed to read data. Read size: $readSize")
                    }
                }
            }
        }.start()
    }

    private fun saveRecordingToDatabase(recording: Recording) {
        Thread {
//            recordingDao.insertRecording(recording)
            Log.i("AudioViewModel", "Recording metadata saved to database.")
        }.start()
    }

    // Start recording audio
    fun startRecording() {
        audioRecord?.startRecording()
        isRecording = true
        startLoudnessLogging()
        Log.i("AudioViewModel", "Recording started.")
    }
    fun stopRecording() {
        audio.stopRecording()
        isRecording = false

        val recordingDetails = audio.getAudioDetails()
        if(recordingDetails!=null){
//            saveRecordingToDatabase(recordingDetails)
        }

        Log.i("AudioViewModel", "Recording stopped.")
    }
    fun pauseRecording() {
        if (isRecording && !audio.isRecordingPaused()) {
            audio.pauseRecording()

            Log.i("AudioViewModel", "Recording paused.")
        }
    }
    fun resumeRecording() {
        if (isRecording && audio.isRecordingPaused()) {
            audio.resumeRecording()
            Log.i("AudioViewModel", "Recording resumed.")
        }
    }
}