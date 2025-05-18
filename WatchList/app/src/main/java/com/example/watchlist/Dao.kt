package com.example.watchlist

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "AdditionalInfo") val additionalInfo: String,
    @ColumnInfo(name = "Description") val description: String,
    @ColumnInfo(name = "IsLike") val isLike: Boolean
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Insert
    suspend fun insertUser(user: User)

    //@Query("DELETE FROM user_table WHERE $id = :userId")
    //suspend fun deleteUser(userId: Int)
}