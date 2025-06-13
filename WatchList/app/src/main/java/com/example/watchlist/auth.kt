package com.example.watchlist
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import at.favre.lib.bytes.Bytes
import at.favre.lib.crypto.bcrypt.BCrypt
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.withContext
import java.security.SecureRandom
import kotlinx.coroutines.flow.flowOn

class AuthViewModel(private val database: AppDatabase2) : ViewModel() {
    val userCredentialsDao: UserCredentialsDao

    init {
        userCredentialsDao = database.UserCredentialsDao()
        viewModelScope.launch(Dispatchers.IO) {
            val existingCredentials = userCredentialsDao.getCredentialsById(1)
            if (existingCredentials == null) {
                userCredentialsDao.insertPass(UserCredentials(id = 1, enabled = false, passwordHash = null))
            }
        }
    }

    private val secureRandom = SecureRandom()

    suspend fun hashPassword(password: String): String = withContext(Dispatchers.Default) {
        BCrypt.with(secureRandom).hashToString(12, password.toCharArray())
    }

    suspend fun checkPassword(password: String, hashedPasswordFromDb: String): Boolean = withContext(Dispatchers.Default) {
        BCrypt.verifyer().verify(password.toCharArray(), hashedPasswordFromDb.toCharArray()).verified
    }

    fun getCredentialsFlow(): Flow<UserCredentials?> {
        return userCredentialsDao.getCredentialsFlowById(1)
            .catch { e -> e.printStackTrace(); emit(null) }
            .flowOn(Dispatchers.IO)
    }

    suspend fun getCredentials(): UserCredentials? = withContext(Dispatchers.IO) {
        userCredentialsDao.getCredentialsById(1)
    }

    // ZMIANA TUTAJ:
    fun updateCredentials(credentials: UserCredentials) { // Ten przyjmuje całą encję, więc typ jest OK, bo w encji masz String?
        viewModelScope.launch(Dispatchers.IO) {
            userCredentialsDao.updatePass(credentials)
        }
    }

    // ZMIANA TUTAJ: Zmień passwordHash: String na passwordHash: String?
    suspend fun insertOrUpdateCredentials(enabled: Boolean, passwordHash: String?) { // <-- KLUCZOWA ZMIANA
        viewModelScope.launch(Dispatchers.IO) {
            val existingCredentials = userCredentialsDao.getCredentialsById(1)
            if (existingCredentials == null) {
                userCredentialsDao.insertPass(UserCredentials(id = 1, enabled = enabled, passwordHash = passwordHash))
            } else {
                userCredentialsDao.updatePass(existingCredentials.copy(enabled = enabled, passwordHash = passwordHash))
            }
        }
    }
}


// 5. ViewModel Factory
class AuthViewModelFactory(val database: AppDatabase2) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(database) as T
    }
}