package com.example.soundmeternew

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recordings")
data class Recording(
        @PrimaryKey(autoGenerate = true) val id: Long = 0,  // Primary key, auto-generated
        val filePath: String,                           // audio file path
        val timestamp: Long,                            // timestamp of creation
        val maxMeasurement: Double                              // max measurement in decibels
    )
