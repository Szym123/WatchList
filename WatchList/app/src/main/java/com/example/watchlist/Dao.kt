package com.example.watchlist

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "AdditionalInfo") val additionalInfo: String,
    @ColumnInfo(name = "Description") val description: String,
    @ColumnInfo(name = "IsLike") var isLike: Boolean,
    @ColumnInfo(name = "Video") val video: String,
    @ColumnInfo(name = "Image") val image: String?,
)


@Entity(tableName = "user_credentials")
data class UserCredentials(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "enabled") val enabled: Boolean,
    @ColumnInfo(name = "password_hash") val passwordHash: String
)

@Dao
interface UserDao {
    @Query("SELECT * FROM user_table")
    fun getAllUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Update
    suspend fun updateUser(user: User)

    @Query("DELETE FROM user_table WHERE id = :userId")
    suspend fun deleteUser(userId: Int)

    @Query("SELECT * FROM user_table WHERE id = :userId")
    suspend fun getUserById(userId: Int): User?

    @Query("SELECT MAX(id) FROM user_table")
    fun getMaxId(): LiveData<Long?>

    @Query("DELETE FROM user_table")
    suspend fun deleteAll()

    @Query("DELETE FROM sqlite_sequence WHERE name = 'user_table'")
    suspend fun deletePrimaryKeyIndex()

    @Query("UPDATE user_table SET IsLike = :isLike WHERE id = :userId")
    suspend fun updateUserLikeById(userId: Long, isLike: Boolean)

    @Query("UPDATE user_table SET Name = :name ,AdditionalInfo = :additionalInfo ,Description = :description ,IsLike = :isLiked ,Video = :video ,Image = :image WHERE id = :userId")
    suspend fun updateUserById(
        userId: Long,
        name: String,
        additionalInfo: String,
        description: String,
        isLiked: Boolean,
        video: String,
        image: String?
    )
}

@Dao
interface UserCredentialsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPass(credentials: UserCredentials)

    @Update
    suspend fun updatePass(credentials: UserCredentials)

    @Query("DELETE FROM user_credentials WHERE id = :id")
    suspend fun deletePass(id: Int)

    @Query("SELECT password_hash FROM user_credentials WHERE id = :id")
    suspend fun getPass(id: Int): String
}
// TO DO: haszowanie i ogólnie obsługa na frontendzie
