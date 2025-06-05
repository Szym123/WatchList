package com.example.watchlist

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration



// Adnotacja @Database mówi Room, że to jest główna klasa bazy danych
@Database(
    entities = [User::class, UserCredentials::class], // Nowa tabela
    version = 1,  // Resetujesz wersję do 1 (bez migracji!)
)
// Musimy odziedziczyć po RoomDatabase() - to wymóg biblioteki Room
abstract class AppDatabase : RoomDatabase() {
    // Każda tabela ma swój "DAO" - tu deklarujemy metody dostępu do tabeli User
    abstract fun userDao(): UserDao

    // Dodajemy nowy DAO dla tabeli z loginami/hasłami
    abstract fun credentialsDao(): UserCredentialsDao

    // --- TERAZ MAGIA SINGLETONU ---
    // Companion object to taki "przyjaciel klasy", który daje nam dostęp do metod
    // BEZ tworzenia instancji AppDatabase (jak statyczne metody w Javie)
    companion object {
        // @Volatile - oznacza, że wartość INSTANCE będzie widoczna od razu dla WSZYSTKICH wątków
        @Volatile
        // INSTANCE - przechowuje naszą jedyną instancję bazy danych
        private var INSTANCE: AppDatabase? = null

        // Metoda getDatabase - główny punkt dostępu do bazy w całej aplikacji
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "watchlist_database"
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}