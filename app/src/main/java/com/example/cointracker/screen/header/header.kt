package com.example.cointracker.screen.header

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cointracker.R


@Composable
fun Header() {
    // Box do Header
    Box(
        modifier = Modifier
            .height(100.dp)  // ajusta a altura
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Black,
                        Color.DarkGray
                    )
                )
            )
    ) {
        // Centralizando a imagem dentro do Box
        Image(
            painter = painterResource(id = R.drawable.ftftp),  // Substitua com o nome da sua imagem
            contentDescription = "Logo",
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.CenterStart)  // Alinha à esquerda
        )

        Button(
            onClick = {},
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            ),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 16.dp)
                .size(120.dp, 60.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 0.dp,
                pressedElevation = 0.dp
            ),
            border = null // Remove a   borda

        ) {
            Text(
                text = "\uD83D\uDC9B",
                fontSize = 24.sp
            )
        }
    }
    Box(
        modifier = Modifier
            .height(2.5.dp)  //O tamanho da linha
            .fillMaxWidth() //linha ocupa toda a largura da tela
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Yellow,
                        Color.DarkGray
                    )
                )
            )
    )
}