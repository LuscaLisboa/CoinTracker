package com.example.cointracker.screen.detailedCoinScreen

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cointracker.model.detailedCrypto
import com.example.cointracker.screen.header.Header
import com.example.cointracker.view.ResponseView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

@Composable
fun DetailedCoinScreen(navController: NavController, id: String?){

    if(id.isNullOrBlank()){
        return (
                Button(modifier = Modifier,
                    onClick = { navController.navigate("MainScreen") }
                ) {
                    Text("Falha ao carregar. Voltar")
                }
                )}

    val client = remember {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    var response by remember { mutableStateOf<List<detailedCrypto?>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val request = Request.Builder()
                .url("https://api.coingecko.com/api/v3/coins/markets?vs_currency=brl&ids=$id")
                .get()
                .addHeader("accept", "application/json")
                .build()

            withContext(Dispatchers.IO){
                try {
                    val result = client.newCall(request).execute()
                    if (result.isSuccessful) {
                        response = result.body?.string()?.let { jsonString ->
                            Gson().fromJson(jsonString, object : TypeToken<List<detailedCrypto>>() {}.type)
                        }
                    } else {
                        error = "GSON Error: ${result.code}"
                    }
                }catch (e: IOException) {
                    error = "Network error: ${e.message}"
                }
            }
        } catch (e: Exception) {
            error = "Error: ${e.message}"
        } finally {
            isLoading = false
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {
        item { Header() }

        item { Spacer(modifier = Modifier.height(20.dp)) }

        when {
            isLoading -> {
                item {
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            error != null -> {
                item {
                    Text(
                        text = error ?: "Unknown error",
                        color = Color.Red,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
            response != null -> {
                item {
                    Box(
                        modifier = Modifier
                            .background(Color.Cyan),
                        contentAlignment = Alignment.Center
                    ) {
                        ResponseView(response = response!!)
                    }
                }

                item {
                    Button(
                        onClick = {
                            val coin = response?.firstOrNull()
                            if (coin != null) {
                                val shareIntent = Intent().apply {
                                    action = Intent.ACTION_SEND
                                    putExtra(Intent.EXTRA_TEXT, "Moeda: ${coin.name}, Valor: ${coin.current_price} BRL")
                                    type = "text/plain"
                                }
                                navController.context.startActivity(Intent.createChooser(shareIntent, "Compartilhar dados"))
                            }
                        },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                    ) {
                        Text(text = "Compartilhar dados")
                    }
                }
            }
        }

        item {
            Button(
                modifier = Modifier.background(Color.Red),
                onClick = { navController.navigate("MainScreen") }
            ) {
                Text("Voltar")
            }
        }
    }
}