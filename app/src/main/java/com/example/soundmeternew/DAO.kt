package com.example.soundmeternew
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

class DAO {

    @Dao
    interface RecordingDao {
        @Insert
        suspend fun insertRecording(recording: Recording.Recording)

        @Query("SELECT * FROM recordings")
        suspend fun getAllRecordings(): List<Recording>

        @Query("SELECT * FROM recordings WHERE id = :id")
        suspend fun getRecordingById(id: Long): Recording
    }

}