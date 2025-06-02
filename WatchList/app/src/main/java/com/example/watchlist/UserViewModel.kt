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


class UserViewModel(database: AppDatabase) : ViewModel() {
    val userDao: UserDao
    val allUsers: LiveData<List<User>>
    val maxUserId: LiveData<Long?>

    init {
        userDao = database.userDao()
        allUsers = userDao.getAllUsers()
        maxUserId = userDao.getMaxId()
    }

    fun insertUser(user: User) {
        // Use viewModelScope to launch coroutine
        viewModelScope.launch(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    fun update(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.updateUser(user) // This is the "overwrite" operation
        }
    }

    fun deleteUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.deleteUser(userId)
        }
    }

    fun getUserById(userId: Int): Deferred<User?> {
        return viewModelScope.async(Dispatchers.IO) {
            userDao.getUserById(userId)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            userDao.deleteAll()
            userDao.deletePrimaryKeyIndex()
        }
    }

    fun updateUserLikeById(userId: Long, currentLikeStatus: Boolean) {
        viewModelScope.launch {
            userDao.updateUserLikeById(userId, !currentLikeStatus)
        }
    }

    fun updateUserById(userId: Long, user: User) {
        viewModelScope.launch {
            userDao.updateUserById(
                userId + 1,
                user.name,
                user.additionalInfo,
                user.description,
                user.isLike,
                user.video,
                user.image
            )
        }
    }
}

// 5. ViewModel Factory
class UserViewModelFactory(val database: AppDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return UserViewModel(database) as T
    }
}