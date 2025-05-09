package com.example.watchlist

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query

@Entity(tableName = "List")
data class MyData(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Second_name") val secondName: String,
    @ColumnInfo(name = "Info") val info: String,
    @ColumnInfo(name = "Is_like") val isLike: Boolean
)

@Dao
interface DataDao {
    @Query("SELECT * FROM List")
    fun getAllData(): LiveData<List<MyData>>

    @Query("SELECT * FROM List WHERE id = :dataId")
    fun getDataById(dataId: Long): MyData?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(user: MyData): Long

    @Query("DELETE FROM List WHERE id = :dataId")
    suspend fun deleteData(dataId: Long): MyData?

    // Add more DAO methods for update and delete as needed
}