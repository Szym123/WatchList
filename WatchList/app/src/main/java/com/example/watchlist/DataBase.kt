package com.example.watchlist

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.Update
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

// 1. Define the Entity (Data Class)
@Entity(tableName = "my_data_table")
data class MyData(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String
)

// 2. Define the DAO (Data Access Object)
@Dao
interface MyDao {
    @Query("SELECT * FROM my_data_table")
    suspend fun getAllData(): List<MyData>

    @Insert
    suspend fun insertData(data: MyData)

    @Update
    suspend fun updateData(data: MyData)

    @Delete
    suspend fun deleteData(data: MyData)

    @Query("SELECT * FROM my_data_table WHERE id = :id")
    suspend fun getDataById(id: Int): MyData?
}

@Database(entities = [MyData::class], version = 3, exportSchema = false) // Increment version here
abstract class AppDatabase : RoomDatabase() {
    abstract fun myDao(): MyDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: android.content.Context): AppDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "app_database"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}