package com.example.cointracker.screen.mainScreen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.cointracker.model.SimpleCrypto
import com.example.cointracker.screen.header.Header
import com.example.cointracker.viewModel.SimpleCryptoViewModel

@Composable
fun MainScreen(navController: NavController) {
    val simpleCryptoViewModel: SimpleCryptoViewModel = viewModel()
    val simpleCryptoList by simpleCryptoViewModel.simpleCryptoList
    val isLoading = simpleCryptoViewModel.isLoading.value
    val errorMessage = simpleCryptoViewModel.errorMessage.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Header()

        Spacer(modifier = Modifier.height(20.dp))

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

@Composable
fun DisplayCoin(crypto: SimpleCrypto, color: Color, navController: NavController) {
    val imageUrl = crypto?.image ?: ""

    val painter = if (imageUrl.isNotBlank()) {
        rememberAsyncImagePainter(model = imageUrl)
    } else {
        Log.e("ResponseView", "URL da imagem inválida ou nula")
        rememberAsyncImagePainter(model = "https://via.placeholder.com/150") // Usando uma URL de fallback
    }

    Row(
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .background(Color.Gray)
            .clickable { navController.navigate("DetailedCoinScreen/${crypto.id}") },
        verticalAlignment = Alignment.CenterVertically, // Alinha verticalmente no centro
    ) {
        // Logo
        Column(
            modifier = Modifier.padding(start = 35.dp), // Ajusta o espaçamento à esquerda
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.fillMaxHeight()
            )
        }

        // Texto
        Spacer(modifier = Modifier.weight(1f)) // Isso vai empurrar o texto para a direita
        Column(
            modifier = Modifier.padding(end = 100.dp), // Reduz o espaçamento à direita para mover o texto ainda mais à esquerda
            verticalArrangement = Arrangement.Center, // Centraliza verticalmente
            horizontalAlignment = Alignment.Start // Alinha o texto à esquerda
        ) {
            Text(
                text = "${crypto.name}",
                color = Color.Black,
                style = TextStyle(fontSize = 25.sp) // Definindo o tamanho da fonte
            )
        }
    }
}

@Composable
fun CoinList(navController: NavController, simpleCryptoList: List<SimpleCrypto>) {
    val favorite = remember { mutableStateOf(null) }

    LazyColumn(modifier = Modifier
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
