package com.example.soundmeternew
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query



    @Dao
    interface RecordingDao {
        @Insert
         fun insertRecording(recording: Recording)

        @Query("SELECT * FROM recordings")
        suspend fun getAllRecordings(): List<Recording>

        @Query("SELECT * FROM recordings WHERE id = :id")
        suspend fun getRecordingById(id: Long): Recording
    }

