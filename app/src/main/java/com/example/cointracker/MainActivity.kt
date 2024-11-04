package com.example.cointracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.cointracker.viewModel.SimpleCrypto
import com.example.cointracker.viewModel.SimpleCryptoViewModel
import com.example.cointracker.ui.theme.CoinTrackerTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoinTrackerTheme {

                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "MainScreen"){

                    composable(route = "MainScreen"){
                        MainScreen(navController)
                    }

                    composable(route = "DetailedCoinScreen/{id}"){
                        backStackEntry ->
                            val id = backStackEntry.arguments?.getString("id")
                        DetailedCoinScreen(navController, id = id)
                    }
                }
            }
        }
    }
}

@Composable
fun Header(){
    Box(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()
            .background(Color.Black),
        contentAlignment = Alignment.Center
    ){
        Text(text = "HEADER",
            color = Color.Yellow
        )
    }
    Box( // 'linha' amarela do Header
        modifier = Modifier
            .height(2.5.dp)
            .fillMaxWidth()
            .background(Color.Yellow)
    )
}

@Composable
fun DetailedCoinScreen(navController: NavController, id: String?){

    if(id.isNullOrBlank())
        return (
            Button(modifier = Modifier
                .background(Color.Red),
                onClick = { navController.navigate("MainScreen") }
            ) {
                Text("Falha ao carregar. Voltar")
            }
    )

    val client = remember {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    var response by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        try {
            val request = Request.Builder()
                .url("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=$id")
                .get()
                .addHeader("accept", "application/json")
                .build()

            withContext(Dispatchers.IO){
                try {
                    val result = client.newCall(request).execute()
                    if (result.isSuccessful) {
                        response = result.body?.string()
                    } else {
                        error = "Error: ${result.code}"
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ) {

        Header()

        Spacer(modifier = Modifier.height(20.dp))

        when{
            isLoading -> {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            error != null -> {
                Text(
                    text = error ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier.padding(16.dp)
                )
            }
            response != null -> {
                Box(modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
                    .background(Color.Cyan),
                    contentAlignment = Alignment.Center
                ){
                    Text(text = response!!)
                }
            }
        }

        Button(modifier = Modifier
            .background(Color.Red),
            onClick = { navController.navigate("MainScreen") }
        ) {
            Text("Voltar")
        }
    }
}

@Composable
fun DisplayCoin(crypto: SimpleCrypto, color: Color, navController: NavController){

    Row(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(color)
            .clickable { navController.navigate("DetailedCoinScreen/${crypto.id}") },
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Top
    ){
        Column (){
            Text(text = "nome:${crypto.name}",
                color = Color.Black)
        }
        Column (){
            Text(text = "Coluna 2")
        }
    }
}

@Composable
fun CoinList(navController: NavController, simpleCryptoList: List<SimpleCrypto>){

    val favorite = remember { mutableStateOf(null) }

    LazyColumn (modifier = Modifier
        .fillMaxSize()
    ) {
        item(favorite) {

        }
        items(simpleCryptoList) { crypto ->
            Spacer(modifier = Modifier.height(20.dp))
            DisplayCoin(crypto, Color.White, navController)
        }
    }
}

@Composable
fun MainScreen(navController: NavController){

    val simpleCryptoViewModel: SimpleCryptoViewModel = viewModel()
    val simpleCryptoList by simpleCryptoViewModel.simpleCryptoList
    val isLoading = simpleCryptoViewModel.isLoading.value
    val errorMessage = simpleCryptoViewModel.errorMessage.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray)
    ){
        Header()

        Spacer(modifier = Modifier.height(20.dp))

        //DisplayCoin("Favorito", Color.Cyan, navController, "")

        when {
            isLoading -> {
                Text(
                    "Loading...",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            errorMessage != null -> {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            }

            else -> {
                CoinList(navController, simpleCryptoList)
            }
        }
    }
}