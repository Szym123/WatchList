package com.example.watchlist


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AuthViewModel(database: AppDatabase2) : ViewModel() {
    val UserCredentials: UserCredentialsDao

    init {
        UserCredentials = database.UserCredentialsDao()
    }

    fun insertUser(password: String) {
        // Use viewModelScope to launch coroutine
        viewModelScope.launch(Dispatchers.IO) {
            UserCredentials.insertPass(true, passwordHash = password)
        }
    }

    fun update(enDis: Boolean,password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            UserCredentials.updatePass(enabled = enDis, passwordHash = password) // This is the "overwrite" operation
        }
    }

    fun deleteUser(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            UserCredentials.deletePass(id)
        }
    }

    suspend fun getPassById(id: Int): String {
        return viewModelScope.async(Dispatchers.IO) {
            UserCredentials.getPass(id)
        }.await()
    }

}

// 5. ViewModel Factory
class AuthViewModelFactory(val database: AppDatabase2) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(database) as T
    }
}