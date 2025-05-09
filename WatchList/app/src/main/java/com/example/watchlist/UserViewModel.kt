package com.example.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// 4. ViewModel
class UserViewModel(application: android.app.Application) : ViewModel() {
    private val userDao: UserDao
    val allUsers: LiveData<List<User>>

    init {
        val database = AppDatabase.getInstance(application)
        userDao = database.userDao()
        allUsers = userDao.getAllUsers()
    }

    fun insertUser(user: User) {
        // Use viewModelScope to launch coroutine
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    /*fun deleteUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.deleteUser(userId)
        }
    }*/
}

// 5. ViewModel Factory
class UserViewModelFactory(private val application: android.app.Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return UserViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}