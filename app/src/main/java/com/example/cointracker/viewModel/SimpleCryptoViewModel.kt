package com.example.cointracker.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import com.example.cointracker.SimpleCryptoDatabase
import com.example.cointracker.model.SimpleCrypto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.launch
import java.io.IOException


class SimpleCryptoViewModel(application: Application) : AndroidViewModel(application) {

    var simpleCryptoList = mutableStateOf(listOf<SimpleCrypto>())
        private set
    var isLoading = mutableStateOf(true)
        private set
    var errorMessage = mutableStateOf<String?>(null)
        private set

    init {
        loadCryptocurrencies(getApplication())
    }

    private fun loadCryptocurrencies(context: Context) {
        viewModelScope.launch {
            isLoading.value = true
            val jsonString = loadJSONFromAsset(context,"SimplifiedCoinList.json")
            if (jsonString != null) {
                simpleCryptoList.value = parseJSON(jsonString)
                errorMessage.value = null
            } else {
                errorMessage.value = "Failed to load cryptocurrency data."
            }
            isLoading.value = false
        }
    }

    private fun loadJSONFromAsset(context: Context, fileName: String): String? {
        return try {
            val inputStream = getApplication<Application>().assets.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            Log.d("SimpleCryptoViewModel", "Successfully loaded JSON data.")
            String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            Log.e("SimpleCryptoViewModel", "Error loading JSON", ex)
            null
        }
    }

    private fun parseJSON(jsonString: String): List<SimpleCrypto> {
        return try {
            val gson = Gson()
            val itemType = object : TypeToken<List<SimpleCrypto>>() {}.type
            gson.fromJson(jsonString, itemType)
        } catch (e: Exception) {
            e.printStackTrace()
            errorMessage.value = "Error parsing JSON data."
            emptyList()
        }
    }

//    lateinit var database : SimpleCryptoDatabase
//        private set
//
//    override fun onCreate(){
//        super.onCreate()
//        instance = this
//        database = Room.databaseBuilder(this, SimpleCryptoDatabase::clas.java,
//            "simpleCrypto-db")
//            .build()
//    }
}
