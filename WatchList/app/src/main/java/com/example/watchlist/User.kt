package com.example.watchlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
//We only want to check you are, who you think you are....
@Entity(tableName = "users")
//Attention on a class name
data class UserAuth(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "password_hash") val passwordHash: String
)