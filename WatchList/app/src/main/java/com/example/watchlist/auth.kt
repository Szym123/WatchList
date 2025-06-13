package com.example.watchlist
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import at.favre.lib.bytes.Bytes
import at.favre.lib.crypto.bcrypt.BCrypt
import kotlinx.coroutines.withContext
import java.security.SecureRandom

class AuthViewModel(database: AppDatabase2) : ViewModel() {
    val UserCredentialsDao: UserCredentialsDao

    init {
        UserCredentialsDao = database.UserCredentialsDao()
    }
    private val secureRandom = SecureRandom() // salt

    suspend fun hashPassword(password: String): Pair<String, String> = withContext(Dispatchers.Default) {
        // in bcrypt salt is "inside" hash (there is no need to keep salt eny where else)
        val saltBytes = Bytes.random(16, secureRandom).array() // salt generating
        val hashedPassword = BCrypt.with(secureRandom).hashToString(12, password.toCharArray()) // hashing passwd with salt
        return@withContext Pair(hashedPassword, "")
    }

    suspend fun checkPassword(password: String, hashedPasswordFromDb: String): Boolean = withContext(Dispatchers.Default) {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hashedPasswordFromDb.toCharArray())
        return@withContext result.verified
    }

    suspend fun getCredentials(): UserCredentials? = withContext(Dispatchers.IO) {
        // Musisz zaktualizować UserCredentialsDao o tę metodę
        UserCredentialsDao.getCredentialsById(1)
    }

    suspend fun updateCredentials(credentials: UserCredentials) {
        // co to robi?????????????????????????????????
        viewModelScope.launch(Dispatchers.IO) {
            UserCredentialsDao.updatePass(credentials)
        }
    }

    suspend fun insertOrUpdateCredentials(enabled: Boolean, passwordHash: String?) {
        viewModelScope.launch(Dispatchers.IO) {
            val existingCredentials = UserCredentialsDao.getCredentialsById(1)
            if (existingCredentials == null) {
                UserCredentialsDao.insertPass(UserCredentials(id = 1, enabled = enabled, passwordHash = passwordHash))
            } else {
                UserCredentialsDao.updatePass(existingCredentials.copy(enabled = enabled, passwordHash = passwordHash))
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