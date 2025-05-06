package com.example.watchlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// 4. ViewModel to manage data and interact with the database
class MyViewModel(application: android.app.Application) : ViewModel() {
    private val myDao: MyDao
    //val allData: MutableLiveData<List<MyData>> = MutableLiveData()
    val allData: MutableState<List<MyData>> = mutableStateOf(emptyList())
    val selectedData: MutableState<MyData?> = mutableStateOf(null) // Add this for selected item

    init {
        val database = AppDatabase.getInstance(application)
        myDao = database.myDao()
        // Load initial data
        loadAllData()
    }

    private fun loadAllData() {
        viewModelScope.launch(Dispatchers.IO) {
            val dataList = myDao.getAllData()
            withContext(Dispatchers.Main) {
                allData.value = dataList
            }
        }
    }

    fun insertData(name: String, description: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newData = MyData(name = name, description = description)
            myDao.insertData(newData)
            loadAllData() // Reload
        }
    }

    fun updateData(data: MyData) {
        viewModelScope.launch(Dispatchers.IO) {
            myDao.updateData(data)
            loadAllData()
            if (selectedData.value?.id == data.id) {
                selectedData.value = data
            }
        }
    }

    fun deleteData(data: MyData) {
        viewModelScope.launch(Dispatchers.IO) {
            myDao.deleteData(data)
            loadAllData()
            if (selectedData.value?.id == data.id) {
                selectedData.value = null
            }
        }
    }

    fun getDataById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val data = myDao.getDataById(id)
            withContext(Dispatchers.Main) {
                selectedData.value = data
            }
        }
    }

    fun clearSelectedData() {
        selectedData.value = null
    }
}