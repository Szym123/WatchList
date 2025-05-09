package com.example.watchlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// 4. ViewModel
class UserViewModel(application: android.app.Application) : ViewModel() {
    private val userDao: DataDao
    val allUsers: LiveData<List<MyData>>

    init {
        val database = AppDatabase.getInstance(application)
        userDao = database.userDao()
        allUsers = userDao.getAllData()
    }

    fun insertData(Data: MyData) {
        // Use viewModelScope to launch coroutine
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertData(Data)
        }
    }

    fun deleteData(dataId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.deleteData(dataId)
        }
    }
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