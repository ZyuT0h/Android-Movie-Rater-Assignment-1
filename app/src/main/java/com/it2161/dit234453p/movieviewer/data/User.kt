package com.it2161.dit234453p.movieviewer.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val userName: String,
    val preferredName: String,
    val password: String
)
