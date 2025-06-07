package com.example.watchlist
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(
    entities = [User::class],
    version = 3,
    autoMigrations = [ AutoMigration (from = 2, to = 3) ],
    exportSchema = true

)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}

@Database(
    entities = [UserCredentials::class],
    version = 2,
    autoMigrations = [ AutoMigration (from = 1, to = 2) ],
    exportSchema = true

)
abstract class AppDatabase2 : RoomDatabase() {
    abstract fun UserCredentialsDao(): UserCredentialsDao
}