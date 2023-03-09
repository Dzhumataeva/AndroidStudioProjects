package com.example.myapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val username: String,
    val email: String,
    val password: String,
    val confPassword: String
)